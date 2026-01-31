package talkPick.core.common.exception.handler;

import talkPick.core.common.exception.ErrorCode;
import talkPick.core.common.exception.TalkPickException;

public class HealthCheckExceptionHandler extends TalkPickException {
    public HealthCheckExceptionHandler(final ErrorCode errorCode) {
        super(errorCode);
    }
}
