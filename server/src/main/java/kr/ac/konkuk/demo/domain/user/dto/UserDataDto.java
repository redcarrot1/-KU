package kr.ac.konkuk.demo.domain.user.dto;

import kr.ac.konkuk.demo.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserDataDto {

    public static Response of(User user) {
        return new UserDataDto.Response(
                user.getNickname(),
                user.getMajor().toString(),
                user.getIntroduction());
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private String nickname;
        private String major;
        private String introduction;
    }
}
