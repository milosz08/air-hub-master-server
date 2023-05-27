/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: UserRepositoryTest.java
 * Last modified: 27/05/2023, 02:21
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

package pl.miloszgilga.domain.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.apache.commons.lang3.RandomUtils;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.ArrayList;
import java.time.ZonedDateTime;

import pl.miloszgilga.AbstractBaseTest;
import pl.miloszgilga.security.GrantedUserRole;

import static org.assertj.core.api.Assertions.assertThat;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@SpringBootTest
class UserRepositoryTest extends AbstractBaseTest {

    @Autowired private IUserRepository userRepository;

    @Autowired
    UserRepositoryTest(PasswordEncoder passwordEncoder) {
        super(passwordEncoder);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @BeforeEach
    void cleanupBeforeEveryTest() {
        userRepository.deleteAll();
    }

    @Test
    void findIfUserByLoginOrEmailExist() {
        // given
        final UserEntity userEntity = createBasicUserEntity(GrantedUserRole.STANDARD);
        userRepository.save(userEntity);

        // when
        final var shouldExistLogin = userRepository.findUserByLoginOrEmail(userEntity.getLogin());
        final var shouldExistEmail = userRepository.findUserByLoginOrEmail(userEntity.getEmailAddress());

        // then
        assertThat(shouldExistLogin.isPresent() && shouldExistEmail.isPresent()).isTrue();
    }

    @Test
    void checkIfUserAlreadyExist() {
        // given
        final UserEntity userEntity = createBasicUserEntity(GrantedUserRole.STANDARD);
        userRepository.save(userEntity);

        final String notExistingLogin = "adamnowak123";
        final String notExistingEmail = "adamnowak123@gmail.com";

        // when
        final var shouldExistLogin = userRepository.checkIfUserAlreadyExist(userEntity.getLogin());
        final var shouldExistEmail = userRepository.checkIfUserAlreadyExist(userEntity.getEmailAddress());
        final var shouldNotExistLogin = userRepository.checkIfUserAlreadyExist(notExistingLogin);
        final var shouldNotExistEmail = userRepository.checkIfUserAlreadyExist(notExistingEmail);

        // then
        assertThat(shouldExistLogin && shouldExistEmail).isTrue();
        assertThat(shouldNotExistLogin && shouldNotExistEmail).isFalse();
    }

    @Test
    void checkIfUserWithSameLoginExist() {
        // given
        final UserEntity userEntity = createBasicUserEntity(GrantedUserRole.STANDARD);
        final UserEntity savedUser = userRepository.save(userEntity);

        final String existingLogin = "jankowalski123";

        // when
        final var shouldExist = userRepository.checkIfUserWithSameLoginExist(existingLogin, savedUser.getId());

        // then
        assertThat(shouldExist).isFalse();
    }

    @Test
    void checkIfUserWithSameEmailExist() {
        // given
        final UserEntity userEntity = createBasicUserEntity(GrantedUserRole.STANDARD);
        final UserEntity savedUser = userRepository.save(userEntity);

        final String existingEmail = "gilgamilosz451@gmail.com";

        // when
        final var shouldExist = userRepository.checkIfUserWithSameEmailExist(existingEmail, savedUser.getId());

        // then
        assertThat(shouldExist).isFalse();
    }

    @Test
    @Transactional
    void checkIfAllNotActivatedAccountIsDeleted() {
        // given
        final List<UserEntity> usersToSave = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            final UserEntity userEntity = UserEntity.builder()
                .firstName("Jan")
                .lastName("Kowalski")
                .login("jankowalski" + i)
                .password(passwordEncoder.encode("SekretneHaslo123@"))
                .emailAddress("jankowalski" + i + "@gmail.com")
                .role(GrantedUserRole.STANDARD)
                .isActivated(RandomUtils.nextBoolean())
                .build();
            usersToSave.add(userEntity);
        }
        userRepository.saveAll(usersToSave);

        // when
        final ZonedDateTime expired = ZonedDateTime.now().minusDays(20);
        userRepository.deleteAllNotActivatedAccount(expired);

        // then
        final List<UserEntity> shouldOnlyNotUsed = userRepository.findAll();
        final List<UserEntity> onlyNotUsed = new ArrayList<>(usersToSave.stream()
            .filter(t -> !t.getActivated())
            .toList());

        final List<UserEntity> onlyUniques = new ArrayList<>(shouldOnlyNotUsed);
        onlyUniques.retainAll(onlyNotUsed);
        onlyNotUsed.removeAll(onlyUniques);

        assertThat(onlyNotUsed.isEmpty()).isTrue();
    }
}
