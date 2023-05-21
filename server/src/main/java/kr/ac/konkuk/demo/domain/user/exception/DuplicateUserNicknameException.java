package kr.ac.konkuk.demo.domain.user.exception;

import kr.ac.konkuk.demo.global.error.ErrorCode;
import kr.ac.konkuk.demo.global.error.exception.ApplicationException;

public class DuplicateUserNicknameException extends ApplicationException {

    public DuplicateUserNicknameException() {
        super(ErrorCode.DUPLICATE_USER_NICKNAME);
    }
}
