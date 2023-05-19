package kr.ac.konkuk.demo.global.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kr.ac.konkuk.demo.global.validation.validator.PasswordValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordValid {

    String message() default "비밀번호는 6자리 이상, 15자리 이하입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default 6;

    int max() default 15;

    String pattern() default "^(?=.*[a-zA-Z])(?=.*\\d).+$";
}