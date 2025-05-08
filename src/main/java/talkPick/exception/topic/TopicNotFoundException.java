package talkPick.exception.topic;

import talkPick.common.error.ErrorCode;
import talkPick.common.error.exception.TalkPickException;

public class TopicNotFoundException extends TalkPickException {
    public TopicNotFoundException(final ErrorCode errorCode) {
      super(errorCode);
    }
}