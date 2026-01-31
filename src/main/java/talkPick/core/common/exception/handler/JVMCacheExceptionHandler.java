package talkPick.core.common.exception.handler;

import talkPick.core.common.exception.ErrorCode;
import talkPick.core.common.exception.TalkPickException;

public class JVMCacheExceptionHandler extends TalkPickException {
    public JVMCacheExceptionHandler(final ErrorCode errorCode) {
        super(errorCode);
    }

    public JVMCacheExceptionHandler(final ErrorCode errorCode, final String message) {
        super(errorCode, message);
    }
}