package talkPick.common.error.exception;


import talkPick.common.error.ErrorCode;

public class UnauthorizedException extends TalkPickException {
  public UnauthorizedException() {
    super(ErrorCode.UNAUTHORIZED);
  }

  public UnauthorizedException(final ErrorCode errorCode) {
    super(errorCode);
  }
}