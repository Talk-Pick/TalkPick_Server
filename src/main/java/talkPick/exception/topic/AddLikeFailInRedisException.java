package talkPick.exception.topic;

import talkPick.common.error.ErrorCode;
import talkPick.common.error.exception.TalkPickException;

public class AddLikeFailInRedisException extends TalkPickException {
    public AddLikeFailInRedisException(final ErrorCode errorCode) {
      super(errorCode);
    }
}