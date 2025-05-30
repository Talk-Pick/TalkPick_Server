package talkPick.domain.topic.exception;

import talkPick.global.error.ErrorCode;
import talkPick.global.error.exception.TalkPickException;

public class DuplicateLikeException extends TalkPickException {
    public DuplicateLikeException(final ErrorCode errorCode) {
        super(errorCode);
    }
}