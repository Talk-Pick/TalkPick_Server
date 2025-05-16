package talkPick.domain.topic.exception;

import talkPick.global.error.ErrorCode;
import talkPick.global.error.exception.TalkPickException;

public class KeywordNotFoundException extends TalkPickException {
    public KeywordNotFoundException(final ErrorCode errorCode) {
      super(errorCode);
    }
}