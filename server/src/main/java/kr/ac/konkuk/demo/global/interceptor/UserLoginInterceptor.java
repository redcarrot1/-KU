package kr.ac.konkuk.demo.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.ac.konkuk.demo.global.error.ErrorCode;
import kr.ac.konkuk.demo.global.error.exception.AuthenticationException;
import kr.ac.konkuk.demo.global.manager.TokenManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@RequiredArgsConstructor
public class UserLoginInterceptor implements HandlerInterceptor {

    private final TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getMethod().equals(HttpMethod.OPTIONS.name())) {
            return true;
        }

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // 토큰 유무 확인
        if (!StringUtils.hasText(authorizationHeader)) {
            throw new AuthenticationException(ErrorCode.EMPTY_AUTHORIZATION);
        }

        // GrantType 이 Bearer 인지 검증
        String[] authorizations = authorizationHeader.split(" ");
        if (authorizations.length < 2 || (!"Bearer".equals(authorizations[0]))) {
            throw new AuthenticationException(ErrorCode.NOT_BEARER_GRANT_TYPE);
        }

        // 토큰 유효성 검증
        String accessToken = authorizations[1];
        if (!tokenManager.validateToken(accessToken)) {
            throw new AuthenticationException(ErrorCode.INVALID_TOKEN);
        }

        return true;
    }
}