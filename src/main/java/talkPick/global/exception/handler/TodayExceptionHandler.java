package talkPick.global.exception.handler;

import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

public class TodayExceptionHandler extends TalkPickException {
    public TodayExceptionHandler(ErrorCode errorCode) {super(errorCode);}
}
