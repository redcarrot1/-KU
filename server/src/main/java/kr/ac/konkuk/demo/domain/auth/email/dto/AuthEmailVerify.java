package kr.ac.konkuk.demo.domain.auth.email.dto;

import lombok.*;

public class AuthEmailVerify {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Boolean isAlreadyExist;
    }

}
