package talkPick.domain.admin.validator.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import talkPick.domain.admin.validator.PasswordConstraintValidator;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordConstraintValidator.class)
public @interface ValidPassword {

    String message() default "Invalid password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
