package talkPick.external.llm.exception;

import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

public class LLMException extends TalkPickException {
    public LLMException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public LLMException(final ErrorCode errorCode, final String message) {
        super(errorCode, message);
    }
}