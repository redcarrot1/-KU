package kr.ac.konkuk.demo.domain.auth.email.exception;

import kr.ac.konkuk.demo.global.error.ErrorCode;
import kr.ac.konkuk.demo.global.error.exception.BusinessException;

public class NotMatchAuthCodeException extends BusinessException {

    public NotMatchAuthCodeException() {
        super(ErrorCode.NOT_MATCH_AUTH_CODE);
    }

}
