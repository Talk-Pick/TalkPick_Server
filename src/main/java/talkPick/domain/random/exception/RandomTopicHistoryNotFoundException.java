package talkPick.domain.random.exception;

import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

public class RandomTopicHistoryNotFoundException extends TalkPickException {
    public RandomTopicHistoryNotFoundException(final ErrorCode errorCode) {
      super(errorCode);
    }
}