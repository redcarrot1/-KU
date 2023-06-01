package kr.ac.konkuk.demo.domain.room.exception;

import kr.ac.konkuk.demo.global.error.ErrorCode;
import kr.ac.konkuk.demo.global.error.exception.NotFoundException;

public class RoomNotFoundException extends NotFoundException {

    public RoomNotFoundException() {
        super(ErrorCode.NOT_FOUND_ROOM);
    }
}
