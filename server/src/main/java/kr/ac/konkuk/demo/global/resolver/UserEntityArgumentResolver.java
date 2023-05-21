package kr.ac.konkuk.demo.global.resolver;

import jakarta.servlet.http.HttpServletRequest;
import kr.ac.konkuk.demo.domain.user.dao.UserRepository;
import kr.ac.konkuk.demo.domain.user.exception.UserNotFoundException;
import kr.ac.konkuk.demo.global.manager.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class UserEntityArgumentResolver implements HandlerMethodArgumentResolver {

    private final TokenManager tokenManager;
    private final UserRepository userRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasUserIdAnnotation = parameter.hasParameterAnnotation(UserEntity.class);
        boolean isLongClass = Long.class.isAssignableFrom(parameter.getParameterType());
        return hasUserIdAnnotation && isLongClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        token = token.split(" ")[1];
        return userRepository.findById(tokenManager.getUserId(token))
                .orElseThrow(UserNotFoundException::new);
    }

}
