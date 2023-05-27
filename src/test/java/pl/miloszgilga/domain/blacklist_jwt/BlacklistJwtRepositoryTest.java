/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: BlacklistJwtRepositoryTest.java
 * Last modified: 25/05/2023, 13:40
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

package pl.miloszgilga.domain.blacklist_jwt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.apache.commons.lang3.time.DateUtils;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.jmpsl.security.jwt.JwtConfig;

import pl.miloszgilga.AbstractBaseTest;
import pl.miloszgilga.security.JwtIssuer;
import pl.miloszgilga.security.GrantedUserRole;
import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.domain.user.IUserRepository;

import java.util.Date;
import java.time.Instant;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@SpringBootTest
class BlacklistJwtRepositoryTest extends AbstractBaseTest {

    @Autowired private IBlacklistJwtRepository blacklistJwtRepository;
    @Autowired private IUserRepository userRepository;
    @Autowired private JwtConfig jwtConfig;
    @Autowired private JwtIssuer jwtIssuer;

    @Autowired
    BlacklistJwtRepositoryTest(PasswordEncoder passwordEncoder) {
        super(passwordEncoder);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
