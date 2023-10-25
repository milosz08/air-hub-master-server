/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.domain.blacklist_jwt;

import org.apache.commons.lang3.time.DateUtils;
import org.jmpsl.security.jwt.JwtConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import pl.miloszgilga.AbstractBaseTest;
import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.domain.user.UserRepository;
import pl.miloszgilga.security.GrantedUserRole;
import pl.miloszgilga.security.JwtIssuer;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BlacklistJwtRepositoryTest extends AbstractBaseTest {
    @Autowired
    private BlacklistJwtRepository blacklistJwtRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private JwtIssuer jwtIssuer;

    @Autowired
    BlacklistJwtRepositoryTest(PasswordEncoder passwordEncoder) {
        super(passwordEncoder);
    }

    @BeforeEach
    void cleanupBeforeEveryTest() {
        userRepository.deleteAll();
    }

    @Test
    void checkIfJwtIsOnBlacklist() {
        // given
        final UserEntity userEntity = createBasicUserEntity(GrantedUserRole.STANDARD);
        final String generatedToken = jwtIssuer.generateTokenForUser(userEntity);
        final Date expiredAt = DateUtils.addMinutes(Date.from(Instant.now()), jwtConfig.getTokenExpiredMinutes());

        final BlacklistJwtEntity blacklistJwt = BlacklistJwtEntity.builder()
            .jwtToken(generatedToken)
            .expiredAt(ZonedDateTime.ofInstant(expiredAt.toInstant(), ZonedDateTime.now().getZone()))
            .build();

        userEntity.persistBlacklistJwt(blacklistJwt);
        userRepository.save(userEntity);

        // when
        final boolean expected = blacklistJwtRepository.checkIfJwtIsOnBlacklist(generatedToken);

        // then
        assertThat(expected).isTrue();
    }

    @Test
    void checkIfJwtIsNotOnBlacklist() {
        // given
        final UserEntity userEntity = createBasicUserEntity(GrantedUserRole.STANDARD);
        final String generatedToken = jwtIssuer.generateTokenForUser(userEntity);

        // when
        final boolean expected = blacklistJwtRepository.checkIfJwtIsOnBlacklist(generatedToken);

        // then
        assertThat(expected).isFalse();
    }

    @Test
    @Transactional
    void checkIfAllExpiredJwtsAreDeleted() {
        // given
        final UserEntity userEntity = createBasicUserEntity(GrantedUserRole.STANDARD);
        final Date expiredAt = DateUtils.addMinutes(Date.from(Instant.now()), jwtConfig.getTokenExpiredMinutes());

        final String notExipredRefresh = jwtIssuer.generateTokenForUser(userEntity);
        final BlacklistJwtEntity notExpiredEntity = BlacklistJwtEntity.builder()
            .jwtToken(notExipredRefresh)
            .expiredAt(ZonedDateTime.ofInstant(expiredAt.toInstant(), ZonedDateTime.now().getZone()))
            .build();

        final String expiredRefresh = jwtIssuer.generateTokenForUser(userEntity);
        final BlacklistJwtEntity expiredEntity = BlacklistJwtEntity.builder()
            .jwtToken(expiredRefresh)
            .expiredAt(ZonedDateTime.ofInstant(expiredAt.toInstant(), ZonedDateTime.now().getZone()).minusYears(20))
            .build();

        userEntity.persistBlacklistJwt(notExpiredEntity);
        userEntity.persistBlacklistJwt(expiredEntity);
        userRepository.save(userEntity);

        // when
        blacklistJwtRepository.deleteAllExpiredJwts();

        // then
        final var shouldHasOne = blacklistJwtRepository.findAll();
        assertThat(shouldHasOne.size() == 1).isTrue();
    }
}
