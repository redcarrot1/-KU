package kr.ac.konkuk.demo.global.error.exception;

import kr.ac.konkuk.demo.global.error.ErrorCode;

public class NotFoundException extends ApplicationException {

    public NotFoundException() {
        super(ErrorCode.NOT_FOUND);
    }

    public NotFoundException(ErrorCode e) {
        super(e);
    }
}