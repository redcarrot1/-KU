package kr.ac.konkuk.demo.domain.auth.email.application;

import kr.ac.konkuk.demo.domain.auth.email.dao.AuthEmailRepository;
import kr.ac.konkuk.demo.domain.auth.email.domain.AuthEmail;
import kr.ac.konkuk.demo.domain.auth.email.dto.AuthEmailVerify;
import kr.ac.konkuk.demo.domain.auth.email.exception.AuthCodeExpiredException;
import kr.ac.konkuk.demo.domain.auth.email.exception.AuthEmailNotFoundException;
import kr.ac.konkuk.demo.domain.auth.email.exception.NotMatchAuthCodeException;
import kr.ac.konkuk.demo.domain.user.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthEmailVerifyService {

    private final AuthEmailRepository authEmailRepository;
    private final UserRepository userRepository;

    public AuthEmailVerify.Response verifyAuthEmail(String code, String email) {
        AuthEmail authEmail = authEmailRepository.findFirstByTargetOrderByCreatedAtDesc(email)
                .orElseThrow(AuthEmailNotFoundException::new);

        if (authEmail.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new AuthCodeExpiredException();
        }
        if (!authEmail.getCode().equals(code)) {
            throw new NotMatchAuthCodeException();
        }
        authEmailRepository.delete(authEmail);

        if (userRepository.existsByEmail(authEmail.getTarget()))
            return new AuthEmailVerify.Response(true);
        else
            return new AuthEmailVerify.Response(false);
    }

}
