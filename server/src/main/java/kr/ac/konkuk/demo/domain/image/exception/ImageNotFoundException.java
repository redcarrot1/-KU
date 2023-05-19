package kr.ac.konkuk.demo.domain.image.exception;

import kr.ac.konkuk.demo.global.error.ErrorCode;
import kr.ac.konkuk.demo.global.error.exception.NotFoundException;

public class ImageNotFoundException extends NotFoundException {

    public ImageNotFoundException() {
        super(ErrorCode.FAIL_CALL_IMAGE);
    }
}
