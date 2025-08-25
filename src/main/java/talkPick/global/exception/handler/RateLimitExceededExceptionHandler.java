package talkPick.global.exception.handler;

import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

public class RateLimitExceededExceptionHandler extends TalkPickException {
    public RateLimitExceededExceptionHandler(final ErrorCode errorCode) {
        super(errorCode);
    }
}
