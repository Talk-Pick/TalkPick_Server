package talkPick.error.exception;


import talkPick.error.ErrorCode;

public class UnauthorizedException extends TalkPickException {
  public UnauthorizedException() {
    super(ErrorCode.UNAUTHORIZED);
  }

  public UnauthorizedException(final ErrorCode errorCode) {
    super(errorCode);
  }
}