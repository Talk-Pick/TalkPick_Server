package talkPick.exception;

import talkPick.error.ErrorCode;
import talkPick.error.exception.TalkPickException;

public class TopicNotFoundException extends TalkPickException {
    public TopicNotFoundException(final ErrorCode errorCode) {
      super(errorCode);
    }
}