package talkPick.domain.topic.exception;

import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

public class CategoryNotFoundException extends TalkPickException {
    public CategoryNotFoundException(final ErrorCode errorCode) {
      super(errorCode);
    }
}