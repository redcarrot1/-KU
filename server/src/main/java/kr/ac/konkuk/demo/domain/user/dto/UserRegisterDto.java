package kr.ac.konkuk.demo.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import kr.ac.konkuk.demo.domain.user.entity.Major;
import kr.ac.konkuk.demo.domain.user.entity.User;
import kr.ac.konkuk.demo.global.validation.annotation.NicknameValid;
import kr.ac.konkuk.demo.global.validation.annotation.PasswordValid;
import kr.ac.konkuk.demo.global.validation.annotation.ValidEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserRegisterDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        @Email(message = "올바른 이메일 형식이 아닙니다.")
        private String email;

        @PasswordValid
        private String password;

        @NotBlank(message = "닉네임은 필수값입니다.")
        @NicknameValid
        private String nickname;

        @NotBlank(message = "학과는 필수값입니다.")
        @ValidEnum(enumClass = Major.class, message = "잘못된 학과명입니다.")
        private String major;

        @NotBlank(message = "imageUrl은 필수값입니다.")
        private String imageUrl;

        private String introduction;

        public User toEntity() {
            return User.builder()
                    .email(email)
                    .password(password)
                    .nickname(nickname)
                    .major(Major.valueOf(major))
                    .imageUrl(imageUrl)
                    .introduction(introduction)
                    .build();
        }
    }

}
