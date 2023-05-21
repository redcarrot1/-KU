package kr.ac.konkuk.demo.domain.user.dto;

import kr.ac.konkuk.demo.global.validation.annotation.PasswordValid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PasswordChangeDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @PasswordValid
        private String password;
    }
}
