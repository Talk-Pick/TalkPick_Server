package talkPick.global.error.exception.auth;

import talkPick.global.error.ErrorCode;
import talkPick.global.error.exception.TalkPickException;

public class AuthException extends TalkPickException {
    public AuthException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
