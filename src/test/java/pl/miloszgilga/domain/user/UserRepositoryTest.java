/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.domain.user;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import pl.miloszgilga.AbstractBaseTest;
import pl.miloszgilga.security.GrantedUserRole;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserRepositoryTest extends AbstractBaseTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserRepositoryTest(PasswordEncoder passwordEncoder) {
        super(passwordEncoder);
    }

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
