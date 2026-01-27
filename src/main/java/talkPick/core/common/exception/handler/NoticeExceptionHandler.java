package talkPick.core.common.exception.handler;

import talkPick.core.common.exception.ErrorCode;
import talkPick.core.common.exception.TalkPickException;

public class NoticeExceptionHandler extends TalkPickException {
    public NoticeExceptionHandler(ErrorCode errorCode) {super(errorCode);}
}