package talkPick.domain.topic.exception;

import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

public class TopicNotFoundException extends TalkPickException {
    public TopicNotFoundException(final ErrorCode errorCode) {
      super(errorCode);
    }
}