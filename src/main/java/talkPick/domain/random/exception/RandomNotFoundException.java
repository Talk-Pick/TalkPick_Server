package talkPick.domain.random.exception;

import talkPick.global.error.ErrorCode;
import talkPick.global.error.exception.TalkPickException;

public class RandomNotFoundException extends TalkPickException {
    public RandomNotFoundException(final ErrorCode errorCode) {
      super(errorCode);
    }
}