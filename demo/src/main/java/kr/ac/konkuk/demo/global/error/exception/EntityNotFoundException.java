package kr.ac.konkuk.demo.global.error.exception;


import kr.ac.konkuk.demo.global.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }

}
