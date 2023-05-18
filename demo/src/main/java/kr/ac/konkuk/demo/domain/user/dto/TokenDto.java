package kr.ac.konkuk.demo.domain.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {

    private String grantType;

    private String accessToken;
}
