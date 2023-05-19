package kr.ac.konkuk.demo.global.error.exception;


import kr.ac.konkuk.demo.global.error.ErrorCode;

public class AuthenticationException extends ApplicationException {

    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }

}
