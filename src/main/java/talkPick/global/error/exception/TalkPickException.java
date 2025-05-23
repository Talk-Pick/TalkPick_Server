package talkPick.global.error.exception;

import lombok.Getter;
import talkPick.global.error.ErrorCode;

@Getter
public class TalkPickException extends RuntimeException {
    private final ErrorCode errorCode;

    public TalkPickException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public TalkPickException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public TalkPickException(ErrorCode errorCode, String customMessage) {
        super(customMessage);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}