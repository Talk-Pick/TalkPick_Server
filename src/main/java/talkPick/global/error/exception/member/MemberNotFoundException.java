package talkPick.global.error.exception.member;

import talkPick.global.error.ErrorCode;
import talkPick.global.error.exception.TalkPickException;

public class MemberNotFoundException extends TalkPickException {
    public MemberNotFoundException(final ErrorCode errorCode) {
      super(errorCode);
    }

    public static class MemberLikedTopicsNotFoundException extends RuntimeException {
        public MemberLikedTopicsNotFoundException(String message) {
            super(message);
        }
    }
}