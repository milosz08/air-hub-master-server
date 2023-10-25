/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.validator.email;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.miloszgilga.domain.user.UserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
class EmailAlreadyExistValidator implements ConstraintValidator<ValidateAlreadyExistingEmail, String> {
    private final UserRepository userRepository;

    @Override
    public boolean isValid(String emailAddress, ConstraintValidatorContext context) {
        if (userRepository.checkIfUserAlreadyExist(emailAddress)) {
            log.error("Attempt to create user with existing email address. Existing email address {}", emailAddress);
            return false;
        }
        return true;
    }
}
