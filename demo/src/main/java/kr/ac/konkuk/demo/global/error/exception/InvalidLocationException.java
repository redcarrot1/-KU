package kr.ac.konkuk.demo.global.error.exception;


import kr.ac.konkuk.demo.global.error.ErrorCode;

public class InvalidLocationException extends BusinessException {

    public InvalidLocationException(ErrorCode errorCode) {
        super(errorCode.getMessage(), errorCode);
    }

}
