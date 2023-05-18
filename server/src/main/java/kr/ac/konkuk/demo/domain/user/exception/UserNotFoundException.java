package kr.ac.konkuk.demo.domain.user.exception;

import kr.ac.konkuk.demo.global.error.ErrorCode;
import kr.ac.konkuk.demo.global.error.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException() {
        super(ErrorCode.NOT_FOUND_USER);
    }
}
