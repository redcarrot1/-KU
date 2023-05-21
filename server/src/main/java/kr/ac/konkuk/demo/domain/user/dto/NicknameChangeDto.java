package kr.ac.konkuk.demo.domain.user.dto;

import kr.ac.konkuk.demo.global.validation.annotation.NicknameValid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class NicknameChangeDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NicknameValid
        private String nickname;
    }
}
