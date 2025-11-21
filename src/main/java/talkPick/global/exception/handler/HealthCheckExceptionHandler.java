package talkPick.global.exception.handler;

import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

public class HealthCheckExceptionHandler extends TalkPickException {
    public HealthCheckExceptionHandler(final ErrorCode errorCode) {
        super(errorCode);
    }
}
