/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.security;

import lombok.RequiredArgsConstructor;
import org.jmpsl.security.user.AuthUser;
import org.springframework.stereotype.Component;
import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.domain.user.UserRepository;
import pl.miloszgilga.exception.AuthException.UserNotFoundException;

@Component
@RequiredArgsConstructor
public class SecurityUtils {
    private final UserRepository userRepository;

    public UserEntity getLoggedUser(AuthUser authUser) {
        return userRepository
            .findUserByLoginOrEmail(authUser.getUsername())
            .orElseThrow(UserNotFoundException::new);
    }
}
