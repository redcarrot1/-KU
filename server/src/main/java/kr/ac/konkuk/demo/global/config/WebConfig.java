package kr.ac.konkuk.demo.global.config;

import kr.ac.konkuk.demo.global.interceptor.UserLoginInterceptor;
import kr.ac.konkuk.demo.global.manager.TokenManager;
import kr.ac.konkuk.demo.global.resolver.UserEntityArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final TokenManager tokenManager;
    private final UserEntityArgumentResolver userEmailArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserLoginInterceptor(tokenManager))
                .addPathPatterns("/api/users/**")
                .excludePathPatterns("/api/users/login", "/api/users/register", "/api/users",
                        "/api/users/images/**", "/css/**", "/*.ico", "/error", "/js/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userEmailArgumentResolver);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "http://localhost:8080",
                        "http://localhost:8081", "http://localhost:8082")
                .allowedHeaders(HttpHeaders.CONTENT_TYPE, HttpHeaders.ACCEPT, HttpHeaders.AUTHORIZATION)
                .allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(),
                        HttpMethod.PATCH.name(), HttpMethod.OPTIONS.name());
    }
}