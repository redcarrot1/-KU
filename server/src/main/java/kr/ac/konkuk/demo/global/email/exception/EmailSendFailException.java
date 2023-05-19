package kr.ac.konkuk.demo.global.email.exception;


import kr.ac.konkuk.demo.global.error.ErrorCode;
import kr.ac.konkuk.demo.global.error.exception.BusinessException;

public class EmailSendFailException extends BusinessException {

    public EmailSendFailException() {
        super(ErrorCode.EMAIL_SEND_FAIL);
    }

}
