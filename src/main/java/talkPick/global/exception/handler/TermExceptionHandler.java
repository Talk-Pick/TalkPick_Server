package talkPick.global.exception.handler;

import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

public class TermExceptionHandler extends TalkPickException {
    public TermExceptionHandler(ErrorCode errorCode) {super(errorCode);}
}
