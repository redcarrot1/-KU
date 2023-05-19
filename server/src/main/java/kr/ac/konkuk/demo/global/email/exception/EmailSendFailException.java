package kr.ac.konkuk.demo.global.email.exception;


import kr.ac.konkuk.demo.global.error.ErrorCode;
import kr.ac.konkuk.demo.global.error.exception.ApplicationException;

public class EmailSendFailException extends ApplicationException {

    public EmailSendFailException() {
        super(ErrorCode.EMAIL_SEND_FAIL);
    }

}
