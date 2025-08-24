package talkPick.global.exception.handler;

import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

public class LLMExceptionHandler extends TalkPickException {
  public LLMExceptionHandler(final ErrorCode errorCode) {
    super(errorCode);
  }

  public LLMExceptionHandler(final ErrorCode errorCode, final String message) {
    super(errorCode, message);
  }
}