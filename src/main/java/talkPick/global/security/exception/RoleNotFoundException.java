package talkPick.global.security.exception;

import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

@Deprecated
public class RoleNotFoundException extends TalkPickException {
    public RoleNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}