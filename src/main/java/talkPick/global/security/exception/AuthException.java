package talkPick.global.security.exception;

import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

public class AuthException extends TalkPickException {
    public AuthException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
