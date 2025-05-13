package talkPick.domain.random.exception;

import talkPick.global.error.ErrorCode;
import talkPick.global.error.exception.TalkPickException;

public class TopicStatNotFoundException extends TalkPickException {
    public TopicStatNotFoundException(final ErrorCode errorCode) {
      super(errorCode);
    }
}