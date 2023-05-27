/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: RefreshTokenRepositoryTest.java
 * Last modified: 27/05/2023, 01:44
 * Project name: air-hub-master-server
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 *
 *     <http://www.apache.org/license/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the license.
 */

package pl.miloszgilga.domain.refresh_token;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.jmpsl.security.jwt.JwtService;
import org.jmpsl.security.jwt.RefreshTokenPayloadDto;

import pl.miloszgilga.AbstractBaseTest;
import pl.miloszgilga.security.GrantedUserRole;
import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.domain.user.IUserRepository;

import static org.assertj.core.api.Assertions.assertThat;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@SpringBootTest
class RefreshTokenRepositoryTest extends AbstractBaseTest {

    @Autowired private IRefreshTokenRepository refreshTokenRepository;
    @Autowired private IUserRepository userRepository;
    @Autowired private JwtService jwtService;

    @Autowired
    RefreshTokenRepositoryTest(PasswordEncoder passwordEncoder) {
        super(passwordEncoder);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
