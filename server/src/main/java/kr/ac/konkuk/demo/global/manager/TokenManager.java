package kr.ac.konkuk.demo.global.manager;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.ac.konkuk.demo.domain.user.dao.UserRepository;
import kr.ac.konkuk.demo.domain.user.dto.TokenDto;
import kr.ac.konkuk.demo.global.error.ErrorCode;
import kr.ac.konkuk.demo.global.error.exception.AuthenticationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenManager {

    @Value("${token.secret}")
    private String tokenSecret;

    private final UserRepository userRepository;

    public TokenDto createTokenDto(Long userId) {
        String accessToken = createAccessToken(userId);
        return TokenDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .build();
    }

    private String createAccessToken(Long userId) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject("accessToken")
                .setAudience(String.valueOf(userId))
                .setIssuer("volunteerKU")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, tokenSecret.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    public Long getUserId(String token) {
        try {
            String strUserId = Jwts.parser().setSigningKey(tokenSecret.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token).getBody().getAudience();
            return Long.valueOf(strUserId);
        } catch (ExpiredJwtException e) {
            log.info("토큰 기한 만료", e);
            throw new AuthenticationException(ErrorCode.EXPIRED_TOKEN);
        } catch (JwtException e) {  // 토큰 변조
            log.info("잘못된 jwt token", e);
            throw new AuthenticationException(ErrorCode.INVALID_TOKEN);
        } catch (Exception e) {
            log.info("jwt token 검증 중 에러 발생", e);
            throw new AuthenticationException(ErrorCode.INVALID_TOKEN);
        }
    }

    public boolean validateToken(String token) {
        return userRepository.existsById(getUserId(token));
    }

}