package kr.ac.konkuk.demo.global.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kr.ac.konkuk.demo.global.validation.validator.LocalDateValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LocalDateValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LocalDateValid {

    String message() default "yyyyMMdd 형식에 맞지 않습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String pattern() default "yyyy-MM-dd";
}