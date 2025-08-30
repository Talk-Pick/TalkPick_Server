package talkPick.global.healthCheck.exception;

import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

@Deprecated
public class HealthCheckException extends TalkPickException {
    public HealthCheckException(final ErrorCode errorCode) {
        super(errorCode);
    }
}