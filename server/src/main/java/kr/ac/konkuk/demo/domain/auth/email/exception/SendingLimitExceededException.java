package kr.ac.konkuk.demo.domain.auth.email.exception;

import kr.ac.konkuk.demo.global.error.ErrorCode;
import kr.ac.konkuk.demo.global.error.exception.BusinessException;

public class SendingLimitExceededException extends BusinessException {

    public SendingLimitExceededException() {
        super(ErrorCode.SENDING_LIMIT_EXCEEDED);
    }

}
