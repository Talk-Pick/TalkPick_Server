package talkPick.batch.topic.exception;

import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

public class JVMCacheException extends TalkPickException {
    public JVMCacheException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public JVMCacheException(final ErrorCode errorCode, final String message) {
        super(errorCode, message);
    }
}