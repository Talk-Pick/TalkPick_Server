package talkPick.core.common.exception.handler;

import talkPick.core.common.exception.ErrorCode;
import talkPick.core.common.exception.TalkPickException;

public class RateLimitExceededExceptionHandler extends TalkPickException {
    public RateLimitExceededExceptionHandler(final ErrorCode errorCode) {
        super(errorCode);
    }
}
