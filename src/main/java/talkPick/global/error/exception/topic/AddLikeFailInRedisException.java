package talkPick.exception.topic;

import talkPick.global.error.ErrorCode;
import talkPick.global.error.exception.TalkPickException;

public class AddLikeFailInRedisException extends TalkPickException {
    public AddLikeFailInRedisException(final ErrorCode errorCode) {
      super(errorCode);
    }
}