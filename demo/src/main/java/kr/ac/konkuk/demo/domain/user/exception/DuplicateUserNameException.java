package kr.ac.konkuk.demo.domain.user.exception;

import kr.ac.konkuk.demo.global.error.exception.ApplicationException;
import kr.ac.konkuk.demo.global.error.ErrorCode;

public class DuplicateUserNameException extends ApplicationException {

    public DuplicateUserNameException() {
        super(ErrorCode.DUPLICATE_USER_NAME);
    }
}
