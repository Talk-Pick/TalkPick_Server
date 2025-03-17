package talkPick.exception;

import talkPick.error.ErrorCode;
import talkPick.error.exception.TalkPickException;

public class AddLikeFailInRedisException extends TalkPickException {
    public AddLikeFailInRedisException(final ErrorCode errorCode) {
      super(errorCode);
    }
}