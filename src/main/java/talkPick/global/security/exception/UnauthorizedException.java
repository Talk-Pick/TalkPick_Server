package talkPick.global.security.exception;


import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

public class UnauthorizedException extends TalkPickException {
  public UnauthorizedException() {
    super(ErrorCode.UNAUTHORIZED);
  }

  public UnauthorizedException(final ErrorCode errorCode) {
    super(errorCode);
  }
}