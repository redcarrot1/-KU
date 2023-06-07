package kr.ac.konkuk.demo.domain.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    private String grantType;
    private String accessToken;
    private String email;
    private String nickname;
    private String major;
    private String introduce;
}
