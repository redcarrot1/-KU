package kr.ac.konkuk.demo.domain.user.exception;

import kr.ac.konkuk.demo.global.error.ErrorCode;
import kr.ac.konkuk.demo.global.error.exception.AuthenticationException;

public class FailUserLoginException extends AuthenticationException {
    public FailUserLoginException() {
        super(ErrorCode.FAIL_LOGIN);
    }
}
