package talkPick.core.common.exception.handler;

import talkPick.core.common.exception.ErrorCode;
import talkPick.core.common.exception.TalkPickException;

public class LLMExceptionHandler extends TalkPickException {
  public LLMExceptionHandler(final ErrorCode errorCode) {
    super(errorCode);
  }

  public LLMExceptionHandler(final ErrorCode errorCode, final String message) {
    super(errorCode, message);
  }
}