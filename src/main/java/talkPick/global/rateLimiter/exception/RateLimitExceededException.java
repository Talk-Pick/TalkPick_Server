package talkPick.global.rateLimiter.exception;

import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

public class RateLimitExceededException extends TalkPickException {
    public RateLimitExceededException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
