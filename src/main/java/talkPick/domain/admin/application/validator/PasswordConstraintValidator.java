package talkPick.domain.admin.application.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.passay.*;
import org.springframework.stereotype.Component;
import talkPick.domain.admin.application.validator.annotation.ValidPassword;

import java.util.Arrays;

@Slf4j
@Component
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    // 관리자 비밀번호 조건
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

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        log.info("======비밀번호 검증 중 ===========: {}", password);

        if (password == null) {
            log.warn("⚠️ 비밀번호가 null입니다.");
            return false;
        }

        RuleResult result = validator.validate(new PasswordData(password));

        if (!result.isValid()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    String.join(", ", validator.getMessages(result))
            ).addConstraintViolation();
            return false; // 검증 실패 시 false 반환
        }
        log.info("✅ 비밀번호 검증 통과");
        return true; // 검증 성공 시 true 반환
    }
}