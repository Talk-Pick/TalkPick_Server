package talkPick.domain.topic.exception;

import talkPick.global.error.ErrorCode;
import talkPick.global.error.exception.TalkPickException;

public class CategoryNotFoundException extends TalkPickException {
    public CategoryNotFoundException(final ErrorCode errorCode) {
      super(errorCode);
    }
}