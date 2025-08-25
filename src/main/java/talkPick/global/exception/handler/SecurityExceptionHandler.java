package talkPick.global.exception.handler;

import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

public class SecurityExceptionHandler extends TalkPickException {
    public SecurityExceptionHandler(final ErrorCode errorCode) {
        super(errorCode);
    }
}
