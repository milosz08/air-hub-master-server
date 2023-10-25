/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.domain.refresh_token;

import org.jmpsl.security.jwt.JwtService;
import org.jmpsl.security.jwt.RefreshTokenPayloadDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.miloszgilga.AbstractBaseTest;
import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.domain.user.UserRepository;
import pl.miloszgilga.security.GrantedUserRole;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RefreshTokenRepositoryTest extends AbstractBaseTest {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;

    @Autowired
    RefreshTokenRepositoryTest(PasswordEncoder passwordEncoder) {
        super(passwordEncoder);
    }

    @BeforeEach
    void cleanupBeforeEveryTest() {
        userRepository.deleteAll();
    }

    @Test
    void findIfRefreshTokenByUserLoginExist() {
        // given
        final UserEntity userEntity = createBasicUserEntity(GrantedUserRole.STANDARD);
        final RefreshTokenPayloadDto refreshToken = jwtService.generateRefreshToken();

        final RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder()
            .token(refreshToken.token())
            .expiredAt(refreshToken.expiredDate())
            .build();

        userEntity.persistRefreshToken(refreshTokenEntity);
        userRepository.save(userEntity);

        // when
        final var shouldExist = refreshTokenRepository.findRefreshTokenByUserLogin(userEntity.getLogin());

        // then
        assertThat(shouldExist.isPresent()).isTrue();
    }

    @Test
    void findIfRefreshTokenByTokenExist() {
        // given
        final UserEntity userEntity = createBasicUserEntity(GrantedUserRole.STANDARD);
        final RefreshTokenPayloadDto refreshToken = jwtService.generateRefreshToken();

        final RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder()
            .token(refreshToken.token())
            .expiredAt(refreshToken.expiredDate())
            .build();

        userEntity.persistRefreshToken(refreshTokenEntity);
        userRepository.save(userEntity);

        // when
        final var shouldExist = refreshTokenRepository.findRefreshTokenByToken(refreshToken.token());

        // then
        assertThat(shouldExist.isPresent()).isTrue();
    }

    @Test
    void findIfRefreshTokenByTokenNotExist() {
        // given
        final UserEntity userEntity = createBasicUserEntity(GrantedUserRole.STANDARD);
        final RefreshTokenPayloadDto expiredRefreshToken = jwtService.generateRefreshToken();

        final RefreshTokenEntity expiredRefreshTokenEntity = RefreshTokenEntity.builder()
            .token(expiredRefreshToken.token())
            .expiredAt(expiredRefreshToken.expiredDate().minusYears(20))
            .build();

        userEntity.persistRefreshToken(expiredRefreshTokenEntity);
        userRepository.save(userEntity);

        // when
        final var shouldNotExist = refreshTokenRepository.findRefreshTokenByToken(expiredRefreshToken.token());

        // then
        assertThat(shouldNotExist.isEmpty()).isTrue();
    }
}
