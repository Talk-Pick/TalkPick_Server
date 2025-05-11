package talkPick.global.error.exception.auth;


import talkPick.global.error.ErrorCode;
import talkPick.global.error.exception.TalkPickException;

public class UnauthorizedException extends TalkPickException {
  public UnauthorizedException() {
    super(ErrorCode.UNAUTHORIZED);
  }

  public UnauthorizedException(final ErrorCode errorCode) {
    super(errorCode);
  }
}