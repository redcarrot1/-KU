package kr.ac.konkuk.demo.domain.image.exception;


import kr.ac.konkuk.demo.global.error.ErrorCode;
import kr.ac.konkuk.demo.global.error.exception.ApplicationException;

public class FailStoreImageException extends ApplicationException {

    public FailStoreImageException() {
        super(ErrorCode.FAIL_STORE_IMAGE);
    }
}
