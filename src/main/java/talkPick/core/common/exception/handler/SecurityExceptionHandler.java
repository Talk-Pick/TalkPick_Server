package talkPick.core.common.exception.handler;

import talkPick.core.common.exception.ErrorCode;
import talkPick.core.common.exception.TalkPickException;

public class SecurityExceptionHandler extends TalkPickException {
    public SecurityExceptionHandler(final ErrorCode errorCode) {
        super(errorCode);
    }
}
