package kr.ac.konkuk.demo.domain.room.exception;

import kr.ac.konkuk.demo.global.error.ErrorCode;
import kr.ac.konkuk.demo.global.error.exception.ApplicationException;

public class FullRoomException extends ApplicationException {

    public FullRoomException() {
        super(ErrorCode.FULL_ROOM_ROOM);
    }
}
