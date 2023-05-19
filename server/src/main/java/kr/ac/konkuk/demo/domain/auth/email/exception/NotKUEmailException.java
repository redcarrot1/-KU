package kr.ac.konkuk.demo.domain.auth.email.exception;

import kr.ac.konkuk.demo.global.error.ErrorCode;
import kr.ac.konkuk.demo.global.error.exception.BusinessException;

public class NotKUEmailException extends BusinessException {

    public NotKUEmailException() {
        super(ErrorCode.NOT_KU_EMAIL);
    }

}
