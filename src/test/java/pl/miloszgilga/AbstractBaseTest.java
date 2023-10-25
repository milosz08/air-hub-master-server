/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga;

import org.springframework.security.crypto.password.PasswordEncoder;
import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.security.GrantedUserRole;

public abstract class AbstractBaseTest {

    protected final PasswordEncoder passwordEncoder;

    protected AbstractBaseTest(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity createBasicUserEntity(GrantedUserRole role, boolean isActivated) {
        return UserEntity.builder()
            .firstName("Jan")
            .lastName("Kowalski")
            .login("jankowalski123")
            .password(passwordEncoder.encode("SekretneHaslo123@"))
            .emailAddress("gilgamilosz451@gmail.com")
            .role(role)
            .isActivated(isActivated)
            .build();
    }

    public UserEntity createBasicUserEntity(GrantedUserRole role) {
        return createBasicUserEntity(role, true);
    }
}
