package talkPick.exception.member;

import talkPick.common.error.ErrorCode;
import talkPick.common.error.exception.TalkPickException;

public class MemberNotFoundException extends TalkPickException {
    public MemberNotFoundException(final ErrorCode errorCode) {
      super(errorCode);
    }
}