/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.domain.ota_token;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jmpsl.security.OtaTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import pl.miloszgilga.AbstractBaseTest;
import pl.miloszgilga.config.ApiProperties;
import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.domain.user.UserRepository;
import pl.miloszgilga.security.GrantedUserRole;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OtaTokenRepositoryTest extends AbstractBaseTest {

    @Autowired
    private OtaTokenRepository otaTokenRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OtaTokenService otaTokenService;
    @Autowired
    private ApiProperties properties;

    @Autowired
    OtaTokenRepositoryTest(PasswordEncoder passwordEncoder) {
        super(passwordEncoder);
    }

    @BeforeEach
    void cleanupBeforeEveryTest() {
        userRepository.deleteAll();
    }

    @Test
    void checkIfTokenAlreadyExistTest() {
        // given
        final String generatedToken = otaTokenService.generateToken();
        final Date expiredAt = DateUtils.addHours(Date.from(Instant.now()), properties.getOtaExpiredRegisterHours());

        final OtaTokenEntity otaTokenEntity = OtaTokenEntity.builder()
            .token(generatedToken)
            .expiredAt(ZonedDateTime.ofInstant(expiredAt.toInstant(), ZonedDateTime.now().getZone()))
            .type(OtaTokenType.ACTIVATE_ACCOUNT)
            .isUsed(false)
            .build();

        final UserEntity userEntity = createBasicUserEntity(GrantedUserRole.STANDARD);
        userEntity.persistOtaToken(otaTokenEntity);
        userRepository.save(userEntity);

        // when
        final boolean shouldExist = otaTokenRepository.checkIfTokenAlreadyExist(generatedToken);

        // then
        assertThat(shouldExist).isTrue();
    }

    @Test
    void findIfTokenByTypeExists() {
        // given
        final String generatedToken = otaTokenService.generateToken();
        final String generatedExpiredMatchToken = otaTokenService.generateToken();
        final String generatedUsedMatchToken = otaTokenService.generateToken();

        final Date expiredAt = DateUtils.addHours(Date.from(Instant.now()), properties.getOtaExpiredRegisterHours());

        final OtaTokenEntity otaTokenEntity = OtaTokenEntity.builder()
            .token(generatedToken)
            .expiredAt(ZonedDateTime.ofInstant(expiredAt.toInstant(), ZonedDateTime.now().getZone()))
            .type(OtaTokenType.ACTIVATE_ACCOUNT)
            .isUsed(false)
            .build();
        final OtaTokenEntity expiredMatchOtaTokenEntity = OtaTokenEntity.builder()
            .token(generatedExpiredMatchToken)
            .expiredAt(ZonedDateTime.ofInstant(expiredAt.toInstant(), ZonedDateTime.now().getZone()).minusYears(20))
            .type(OtaTokenType.ACTIVATE_ACCOUNT)
            .isUsed(false)
            .build();
        final OtaTokenEntity isUsedMatchOtaTokenEntity = OtaTokenEntity.builder()
            .token(generatedUsedMatchToken)
            .expiredAt(ZonedDateTime.ofInstant(expiredAt.toInstant(), ZonedDateTime.now().getZone()))
            .type(OtaTokenType.ACTIVATE_ACCOUNT)
            .isUsed(true)
            .build();

        final UserEntity userEntity = createBasicUserEntity(GrantedUserRole.STANDARD);
        userEntity.persistOtaToken(otaTokenEntity);
        userEntity.persistOtaToken(expiredMatchOtaTokenEntity);
        userEntity.persistOtaToken(isUsedMatchOtaTokenEntity);

        userRepository.save(userEntity);

        // when
        final var shouldExist = otaTokenRepository.findTokenByType(generatedToken, OtaTokenType.ACTIVATE_ACCOUNT);
        final var shouldNotExist = otaTokenRepository.findTokenByType(generatedToken, OtaTokenType.RESET_PASSWORD);
        final var shouldNotExistExp = otaTokenRepository.findTokenByType(generatedExpiredMatchToken, OtaTokenType.ACTIVATE_ACCOUNT);
        final var shouldNotExistUsed = otaTokenRepository.findTokenByType(generatedUsedMatchToken, OtaTokenType.ACTIVATE_ACCOUNT);

        // then
        assertThat(shouldExist.isPresent()).isTrue();
        assertThat(shouldNotExist.isEmpty()).isTrue();
        assertThat(shouldNotExistExp.isEmpty()).isTrue();
        assertThat(shouldNotExistUsed.isEmpty()).isTrue();
    }

    @Test
    @Transactional
    void checkIfNonUsedOtaTokensDeleted() {
        // given
        final List<OtaTokenEntity> tokensToSave = new ArrayList<>();
        final Date expiredAt = DateUtils.addHours(Date.from(Instant.now()), properties.getOtaExpiredRegisterHours());

        for (int i = 0; i < 20; i++) {
            final OtaTokenEntity otaTokenEntity = OtaTokenEntity.builder()
                .token(otaTokenService.generateToken())
                .expiredAt(ZonedDateTime.ofInstant(expiredAt.toInstant(), ZonedDateTime.now().getZone()))
                .type(OtaTokenType.values()[RandomUtils.nextInt(0, OtaTokenType.values().length)])
                .isUsed(RandomUtils.nextBoolean())
                .build();
            tokensToSave.add(otaTokenEntity);
        }
        final UserEntity userEntity = createBasicUserEntity(GrantedUserRole.STANDARD);
        for (final OtaTokenEntity otaTokenEntity : tokensToSave) {
            userEntity.persistOtaToken(otaTokenEntity);
        }
        userRepository.save(userEntity);

        // when
        otaTokenRepository.deleteNonUsedOtaTokens();

        // then
        final List<OtaTokenEntity> shouldOnlyNotUsed = otaTokenRepository.findAll();
        final List<OtaTokenEntity> onlyNotUsed = new ArrayList<>(tokensToSave.stream()
            .filter(t -> !t.getUsed())
            .toList());

        final List<OtaTokenEntity> onlyUniques = new ArrayList<>(shouldOnlyNotUsed);
        onlyUniques.retainAll(onlyNotUsed);
        onlyNotUsed.removeAll(onlyUniques);

        assertThat(onlyNotUsed.isEmpty()).isTrue();
    }
}
