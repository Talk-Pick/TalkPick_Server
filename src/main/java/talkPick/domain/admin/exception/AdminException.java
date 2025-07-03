package talkPick.domain.admin.exception;

import lombok.Getter;
import talkPick.global.exception.ErrorCode;

@Getter
public class AdminException extends RuntimeException {
    private final ErrorCode errorCode;

    public AdminException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public AdminException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}