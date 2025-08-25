package talkPick.global.exception.handler;

import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

public class NoticeExceptionHandler extends TalkPickException {
    public NoticeExceptionHandler(ErrorCode errorCode) {super(errorCode);}
}