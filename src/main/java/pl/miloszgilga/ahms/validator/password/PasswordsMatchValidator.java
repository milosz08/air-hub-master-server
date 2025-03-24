package pl.miloszgilga.ahms.validator.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
class PasswordsMatchValidator implements ConstraintValidator<ValidateMatchingPasswords, PasswordsMatchValidatorModel> {
    @Override
    public boolean isValid(PasswordsMatchValidatorModel value, ConstraintValidatorContext context) {
        if (Objects.isNull(value.getPassword()) || Objects.isNull(value.getConfirmedPassword()) ||
            !value.getPassword().equals(value.getConfirmedPassword())) {
            log.error("Password and confirmed passwords are not the same.");
            return false;
        }
        return true;
    }
}
