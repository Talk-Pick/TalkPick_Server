package talkPick.domain.topic.exception;

import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

public class DuplicateLikeException extends TalkPickException {
    public DuplicateLikeException(final ErrorCode errorCode) {
        super(errorCode);
    }
}