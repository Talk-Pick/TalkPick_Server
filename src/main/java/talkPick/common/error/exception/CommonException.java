package talkPick.common.error.exception;

import lombok.Getter;
import talkPick.common.error.ErrorCode;

@Getter
public class CommonException extends RuntimeException {
    private final ErrorCode errorCode;

    public CommonException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CommonException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
