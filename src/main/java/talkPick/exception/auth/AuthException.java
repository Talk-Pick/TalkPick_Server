package talkPick.exception.auth;

import talkPick.common.error.ErrorCode;
import talkPick.common.error.exception.TalkPickException;

public class AuthException extends TalkPickException {
    public AuthException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
