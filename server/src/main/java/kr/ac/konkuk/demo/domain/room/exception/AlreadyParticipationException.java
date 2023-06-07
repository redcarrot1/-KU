package kr.ac.konkuk.demo.domain.room.exception;

import kr.ac.konkuk.demo.global.error.ErrorCode;
import kr.ac.konkuk.demo.global.error.exception.ApplicationException;

public class AlreadyParticipationException extends ApplicationException {

    public AlreadyParticipationException() {
        super(ErrorCode.ALREADY_PARTICIPATION_ROOM);
    }
}
