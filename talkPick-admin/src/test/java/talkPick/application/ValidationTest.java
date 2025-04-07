package talkPick.application;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import talkPick.adapter.in.dto.AdminReqDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ValidationTest {
    private Validator validator;

    @BeforeEach
    void setUp() { // validator 객체 생성
        ValidatorFactory factory = Validation
                .byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory();

        validator = factory.getValidator();
    }

    @Test
    void 회원가입_value_검증() {
        AdminReqDTO.Signup signup = new AdminReqDTO.Signup("testing", "ValidPassword123!");

        Set<ConstraintViolation<AdminReqDTO.Signup>> violations = validator.validate(signup);

        for (ConstraintViolation<AdminReqDTO.Signup> violation : violations) {
            System.out.println("=== 🚨 검증 오류 : " + violation.getMessage());
        }

        assertFalse(violations.isEmpty()); // validation fail
    }
}
