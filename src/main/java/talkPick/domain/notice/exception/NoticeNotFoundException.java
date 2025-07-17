package talkPick.domain.notice.exception;

import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

public class NoticeNotFoundException extends TalkPickException {
  public NoticeNotFoundException(final ErrorCode errorCode) {
    super(errorCode);
  }
}