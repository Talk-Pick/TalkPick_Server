package talkPick.global.error.exception.admin;

import lombok.Getter;
import talkPick.global.error.ErrorCode;

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