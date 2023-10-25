/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.validator.login;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.miloszgilga.domain.user.UserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
class LoginAlreadyExistValidator implements ConstraintValidator<ValidateAlreadyExistingLogin, String> {
    private final UserRepository userRepository;

    @Override
    public boolean isValid(String login, ConstraintValidatorContext context) {
        if (userRepository.checkIfUserAlreadyExist(login)) {
            log.error("Attempt to create user with existing login. Existing login {}", login);
            return false;
        }
        return true;
    }
}
