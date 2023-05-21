package kr.ac.konkuk.demo.global.validation.validator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kr.ac.konkuk.demo.global.validation.annotation.NicknameValid;

import java.util.regex.Pattern;

public class NicknameValidator implements ConstraintValidator<NicknameValid, String> {

    private int min;
    private int max;
    private String pattern;

    @Override
    public void initialize(NicknameValid constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        if (value.length() < this.min || value.length() > this.max) {
            String message = "닉네임은 " + this.min + "자리 이상, " + this.max + " 자리 이하여야 합니다.";
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
            return false;
        }

        if (!Pattern.matches(pattern, value)) {
            String message = "닉네임은 한글, 영어, 숫자만 허용됩니다.";
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
