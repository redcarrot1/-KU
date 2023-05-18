package kr.ac.konkuk.demo.global.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kr.ac.konkuk.demo.global.validation.annotation.PasswordValid;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

@Slf4j
public class PasswordValidator implements ConstraintValidator<PasswordValid, String> {

    private int min;
    private int max;
    private String pattern;

    @Override
    public void initialize(PasswordValid constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.length() < this.min || value.length() > this.max) {
            String message = "비밀번호는 " + this.min + "자리 이상, " + this.max + " 자리 이하입니다.";
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
            return false;
        }

        if (!Pattern.matches(pattern, value)) {
            String message = "비밀번호는 영문과 숫자의 조합이어야 합니다.";
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}