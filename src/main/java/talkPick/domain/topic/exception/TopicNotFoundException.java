package talkPick.domain.topic.exception;

import talkPick.global.error.ErrorCode;
import talkPick.global.error.exception.TalkPickException;

public class TopicNotFoundException extends TalkPickException {
    public TopicNotFoundException(final ErrorCode errorCode) {
      super(errorCode);
    }
}