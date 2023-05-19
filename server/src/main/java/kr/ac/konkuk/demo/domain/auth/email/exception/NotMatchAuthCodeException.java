package kr.ac.konkuk.demo.domain.auth.email.exception;

import kr.ac.konkuk.demo.global.error.ErrorCode;
import kr.ac.konkuk.demo.global.error.exception.ApplicationException;

public class NotMatchAuthCodeException extends ApplicationException {

    public NotMatchAuthCodeException() {
        super(ErrorCode.NOT_MATCH_AUTH_CODE);
    }

}
