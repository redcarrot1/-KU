package kr.ac.konkuk.demo.global.error.exception;


import kr.ac.konkuk.demo.global.error.ErrorCode;

public class InvalidValueException extends BusinessException {

    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode.getMessage(), errorCode);
    }

}
