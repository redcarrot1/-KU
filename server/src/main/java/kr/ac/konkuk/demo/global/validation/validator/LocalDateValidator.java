package kr.ac.konkuk.demo.global.validation.validator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kr.ac.konkuk.demo.global.validation.annotation.LocalDateValid;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateValidator implements ConstraintValidator<LocalDateValid, String> {

    private String pattern;

    @Override
    public void initialize(LocalDateValid constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            LocalDate.parse(value, DateTimeFormatter.ofPattern(this.pattern));
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
