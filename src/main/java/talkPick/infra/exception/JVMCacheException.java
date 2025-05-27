package talkPick.infra.exception;

import talkPick.global.error.ErrorCode;
import talkPick.global.error.exception.TalkPickException;

public class JVMCacheException extends TalkPickException {
    public JVMCacheException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public JVMCacheException(final ErrorCode errorCode, final String message) {
        super(errorCode, message);
    }
}