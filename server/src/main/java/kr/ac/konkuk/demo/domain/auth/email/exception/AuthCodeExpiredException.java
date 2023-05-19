package kr.ac.konkuk.demo.domain.auth.email.exception;

import kr.ac.konkuk.demo.global.error.ErrorCode;
import kr.ac.konkuk.demo.global.error.exception.ApplicationException;

public class AuthCodeExpiredException extends ApplicationException {

    public AuthCodeExpiredException() {
        super(ErrorCode.EXPIRED_AUTH_CODE);
    }

}
