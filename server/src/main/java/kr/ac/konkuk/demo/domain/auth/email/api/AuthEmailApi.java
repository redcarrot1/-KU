package kr.ac.konkuk.demo.domain.auth.email.api;

import jakarta.validation.Valid;
import kr.ac.konkuk.demo.domain.auth.email.application.AuthEmailSendService;
import kr.ac.konkuk.demo.domain.auth.email.application.AuthEmailVerifyService;
import kr.ac.konkuk.demo.domain.auth.email.dto.AuthEmailSendDto;
import kr.ac.konkuk.demo.domain.auth.email.exception.NotKUEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/emails")
public class AuthEmailApi {

    private final AuthEmailSendService authEmailSendService;
    private final AuthEmailVerifyService authEmailVerifyService;

    @PostMapping
    public AuthEmailSendDto.Response authEmailSend(@RequestBody @Valid AuthEmailSendDto.Request requestDto) {
        if (!requestDto.getTarget().split("@")[1].equals("konkuk.ac.kr")) {
            throw new NotKUEmailException();
        }
        return authEmailSendService.sendAuthEmail(requestDto);
    }

    @GetMapping
    public void authEmailVerify(@RequestParam("code") String code,
                                @RequestParam("email") String email) {
        authEmailVerifyService.verifyAuthEmail(code, email);
    }

}
