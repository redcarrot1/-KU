package kr.ac.konkuk.demo.global.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kr.ac.konkuk.demo.global.validation.validator.NicknameValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NicknameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NicknameValid {

    String message() default "닉네임이 올바르지 않습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default 2;

    int max() default 8;

    String pattern() default "^[0-9a-zA-Z가-힣]*$";
}