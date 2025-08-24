package talkPick.global.exception.handler;

import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

public class JVMCacheExceptionHandler extends TalkPickException {
    public JVMCacheExceptionHandler(final ErrorCode errorCode) {
        super(errorCode);
    }

    public JVMCacheExceptionHandler(final ErrorCode errorCode, final String message) {
        super(errorCode, message);
    }
}