package talkPick.exception;

import talkPick.error.ErrorCode;
import talkPick.error.exception.TalkPickException;

public class MemberNotFoundException extends TalkPickException {
    public MemberNotFoundException(final ErrorCode errorCode) {
      super(errorCode);
    }
}