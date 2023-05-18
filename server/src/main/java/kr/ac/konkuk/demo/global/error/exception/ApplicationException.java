package kr.ac.konkuk.demo.global.error.exception;

import kr.ac.konkuk.demo.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private final ErrorCode error;

    public ApplicationException(ErrorCode e) {
        super(e.getMessage());
        this.error = e;
    }
}