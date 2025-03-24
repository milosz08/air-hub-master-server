package pl.miloszgilga.ahms.validator.password;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordsMatchValidator.class)
@Documented
public @interface ValidateMatchingPasswords {
    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
