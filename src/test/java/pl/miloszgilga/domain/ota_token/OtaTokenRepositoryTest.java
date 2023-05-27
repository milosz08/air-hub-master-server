/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: OtaTokenRepositoryTest.java
 * Last modified: 26/05/2023, 23:35
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

package pl.miloszgilga.domain.ota_token;

import org.junit.jupiter.api.Test;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateUtils;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.time.Instant;
import java.time.ZonedDateTime;

import org.jmpsl.security.OtaTokenService;

import pl.miloszgilga.AbstractBaseTest;
import pl.miloszgilga.config.ApiProperties;
import pl.miloszgilga.domain.user.IUserRepository;
import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.security.GrantedUserRole;

import static org.assertj.core.api.Assertions.assertThat;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@SpringBootTest
class OtaTokenRepositoryTest extends AbstractBaseTest {

    @Autowired private IOtaTokenRepository otaTokenRepository;
    @Autowired private IUserRepository userRepository;
    @Autowired private OtaTokenService otaTokenService;
    @Autowired private ApiProperties properties;

    @Autowired
    OtaTokenRepositoryTest(PasswordEncoder passwordEncoder) {
        super(passwordEncoder);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
