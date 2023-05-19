package kr.ac.konkuk.demo.domain.auth.email.exception;

import kr.ac.konkuk.demo.global.error.ErrorCode;
import kr.ac.konkuk.demo.global.error.exception.BusinessException;

public class AuthCodeExpiredException extends BusinessException {

    public AuthCodeExpiredException() {
        super(ErrorCode.EXPIRED_AUTH_CODE);
    }

}
