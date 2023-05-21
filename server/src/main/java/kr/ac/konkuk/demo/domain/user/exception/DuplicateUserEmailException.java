package kr.ac.konkuk.demo.domain.user.exception;

import kr.ac.konkuk.demo.global.error.exception.ApplicationException;
import kr.ac.konkuk.demo.global.error.ErrorCode;

public class DuplicateUserEmailException extends ApplicationException {

    public DuplicateUserEmailException() {
        super(ErrorCode.DUPLICATE_USER_EMAIL);
    }
}
