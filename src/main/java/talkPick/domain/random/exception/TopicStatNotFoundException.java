package talkPick.domain.random.exception;

import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

public class TopicStatNotFoundException extends TalkPickException {
    public TopicStatNotFoundException(final ErrorCode errorCode) {
      super(errorCode);
    }
}