package talkPick.application.validator;

import lombok.extern.slf4j.Slf4j;
import org.passay.*;
import talkPick.application.validator.annotation.ValidPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

@Slf4j
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {

        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 20),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new RepeatCharactersRule(3),
                new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 3, false),
                new IllegalSequenceRule(EnglishSequenceData.Numerical, 3, false)
        ));

        RuleResult result = validator.validate(new PasswordData(password));

        if (result.isValid()) {
            return true;
        }

        // 검증 실패 시 메시지 적용
        context.buildConstraintViolationWithTemplate(ValidationMessages.PASSWORD_VALIDATION)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        return false;
    }
}