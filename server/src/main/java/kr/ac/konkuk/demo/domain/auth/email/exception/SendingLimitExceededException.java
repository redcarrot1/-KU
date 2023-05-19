package kr.ac.konkuk.demo.domain.auth.email.exception;

import kr.ac.konkuk.demo.global.error.ErrorCode;
import kr.ac.konkuk.demo.global.error.exception.ApplicationException;

public class SendingLimitExceededException extends ApplicationException {

    public SendingLimitExceededException() {
        super(ErrorCode.SENDING_LIMIT_EXCEEDED);
    }

}
