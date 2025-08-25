package talkPick.domain.random.exception;

import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

@Deprecated
public class RandomNotFoundException extends TalkPickException {
    public RandomNotFoundException(final ErrorCode errorCode) {
      super(errorCode);
    }
}