package kr.ac.konkuk.demo.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // Common
    ENTITY_NOT_FOUND(HttpStatus.BAD_REQUEST, "C001", "엔티티 조회에 실패하였습니다."),
    INVALID_VALUE(HttpStatus.BAD_REQUEST, "C002", "잘못된 입력값입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C003", "잘못된 HTTP 메서드입니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "C004", "권한이 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C005", "서버 내부에서 에러가 발생하였습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "C006", "Not Found"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "C007", "Bad Request"),
    INCORRECT_RESULT_SIZE_DATA_ACCESS(HttpStatus.INTERNAL_SERVER_ERROR, "C008", "조회한 데이터의 개수가 의도했던 개수와 다릅니다.(관리자 문의)"),

    // Authentication
    EMPTY_AUTHORIZATION(HttpStatus.UNAUTHORIZED, "A001", "Authorization Header가 빈 값입니다."),
    NOT_BEARER_GRANT_TYPE(HttpStatus.UNAUTHORIZED, "A002", "인증 타입이 Bearer 타입이 아닙니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "A003", "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "A004", "만료된 토큰입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "A005", "Unauthorized"),

    FAIL_LOGIN(HttpStatus.BAD_REQUEST, "L001", "로그인을 실패했습니다."),

    // Email
    NOT_KU_EMAIL(HttpStatus.BAD_REQUEST, "E001", "건국대학교 이메일이 아닙니다."),
    EMAIL_SEND_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "E002", "이메일 전송에 실패하였습니다."),
    EXPIRED_AUTH_CODE(HttpStatus.BAD_REQUEST, "E003", "만료된 코드입니다."),
    SENDING_LIMIT_EXCEEDED(HttpStatus.TOO_MANY_REQUESTS, "E004", "인증 메일 전송 횟수를 초과하였습니다."),
    NOT_MATCH_AUTH_CODE(HttpStatus.BAD_REQUEST, "E005", "코드가 일치하지 않습니다."),


    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "U001", "해당 사용자를 찾을 수 없습니다."),
    DUPLICATE_USER_NAME(HttpStatus.BAD_REQUEST, "U002", "회원 이름은 중복될 수 없습니다."),
    NOT_FOUND_TEAM(HttpStatus.BAD_REQUEST, "U003", "팀을 찾을 수 없습니다."),

    NOT_FORMAT_MATCH_USER_PASSWORD(HttpStatus.BAD_REQUEST, "P001", "비밀번호는 숫자 4자리여야 합니다."),
    NOT_FORMAT_MATCH_ADMIN_PASSWORD(HttpStatus.BAD_REQUEST, "P002", "비밀번호는 5자리 이상, 20자리 이하여야 합니다."),
    INCORRECT_PASSWORD(HttpStatus.BAD_REQUEST, "P003", "비밀번호가 일치하지 않습니다."),

    INVALID_DAY_OF_WEEK(HttpStatus.BAD_REQUEST, "AT01", "출석은 일요일만 가능합니다."),
    DUPLICATE_ATTENDANCE(HttpStatus.BAD_REQUEST, "AT02", "금일은 이미 출석했습니다."),

    FAIL_STORE_IMAGE(HttpStatus.BAD_REQUEST, "I001", "이미지 저장에 실패했습니다."),
    FAIL_CALL_IMAGE(HttpStatus.BAD_REQUEST, "I002", "이미지를 불러오는데 실패했습니다."),

    NOT_FOUND_POST(HttpStatus.BAD_REQUEST, "PO01", "게시물을 찾을 수 없습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;

}
