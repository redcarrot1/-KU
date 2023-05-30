package kr.ac.konkuk.demo.domain.user.dto;

import jakarta.validation.constraints.Email;
import kr.ac.konkuk.demo.global.validation.annotation.PasswordValid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PasswordChangeWithoutLoginDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        private String email;

        @PasswordValid
        private String password;
    }
}
