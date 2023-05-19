package kr.ac.konkuk.demo.domain.auth.email.application;

import kr.ac.konkuk.demo.domain.auth.email.dao.AuthEmailRepository;
import kr.ac.konkuk.demo.domain.auth.email.domain.AuthEmail;
import kr.ac.konkuk.demo.domain.auth.email.dto.AuthEmailSendDto;
import kr.ac.konkuk.demo.domain.auth.email.exception.SendingLimitExceededException;
import kr.ac.konkuk.demo.global.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthEmailSendService {

    /**
     * 1. redis로 변환 필요
     * 2. 인증 횟수에 따른 저장소 entity를 삭제->추가 또는 수정하는 것으로 변경
     */
    private final EmailService emailService;
    private final AuthEmailRepository authEmailRepository;

    @Transactional
    public AuthEmailSendDto.Response sendAuthEmail(AuthEmailSendDto.Request requestDto) {
        String target = requestDto.getTarget();

        Long sendingCount = validateSendingCount(target);

        String title = "[봉사하자KU] 회원가입 인증코드입니다.";
        String code = createCode();
        String content = createContent(code);

        emailService.sendMessage(target, title, content);

        LocalDateTime expiredAt = LocalDateTime.now().plusMinutes(5); // 5분
        AuthEmail authEmail = AuthEmail.builder()
                .target(target)
                .code(code)
                .expiredAt(expiredAt)
                .build();
        authEmailRepository.save(authEmail);

        // 현재 전송 횟수 포함하여 반환
        return AuthEmailSendDto.Response.of(expiredAt, sendingCount + 1);
    }

    /**
     * 하루에 인증 메일 전송은 5회까지 가능합니다.
     * 5회 초과 시 예외를 던집니다.
     *
     * @param target 인증 메일 전송 대상
     */
    private Long validateSendingCount(String target) {
        LocalDateTime today = LocalDate.now().atStartOfDay();
        Long sendingCount = authEmailRepository.countByTargetAndCreatedAtAfter(target, today);
        if (sendingCount >= 5) throw new SendingLimitExceededException();
        return sendingCount;
    }

    private String createContent(String code) {
        String content = "<div>" +
                "<h2>안녕하세요, 봉사하자KU 입니다.</h1>" +
                "<div font-family:verdana'>" +
                "<p>아래 인증코드를 회원가입 창으로 돌아가 입력해주세요.<p>" +
                "<p>회원가입 인증코드입니다.<p>" +
                "<div style='font-size:130%'>" +
                "<strong>" +
                code +
                "</strong>" +
                "<div>" +
                "<br> " +
                "</div>" +
                "<br>" +
                "</div>";
        return content;
    }

    private static String createCode() {
        StringBuilder code = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            int type = random.nextInt(3);

            switch (type) {
                case 0:
                    // 알파벳 소문자(a~z)
                    code.append((char) (int) (random.nextInt(26) + 97));
                    break;
                case 1:
                    // 알파벳 대문자(A~Z)
                    code.append((char) (random.nextInt(26) + 65));
                    break;
                case 2:
                    // 정수 0~9
                    code.append(random.nextInt(10));
                    break;
            }
        }

        return code.toString();
    }

}
