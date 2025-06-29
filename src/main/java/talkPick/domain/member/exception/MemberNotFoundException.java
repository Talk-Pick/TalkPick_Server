package talkPick.domain.member.exception;

import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

public class MemberNotFoundException extends TalkPickException {
    public MemberNotFoundException(final ErrorCode errorCode) {
      super(errorCode);
    }

    public static class MemberLikedTopicsNotFoundException extends RuntimeException {
        public MemberLikedTopicsNotFoundException(String message) {
            super(message);
        }
    }

    public static class MemberTopicResultsNotFoundException extends RuntimeException {
        public MemberTopicResultsNotFoundException(String message) {super(message);}
    }
}