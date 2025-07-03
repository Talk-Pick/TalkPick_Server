package talkPick.domain.topic.exception;

import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

public class KeywordNotFoundException extends TalkPickException {
    public KeywordNotFoundException(final ErrorCode errorCode) {
      super(errorCode);
    }
}