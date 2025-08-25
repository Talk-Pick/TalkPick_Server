package talkPick.global.exception.handler;

import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

public class MemberExceptionHandler extends TalkPickException {
    public MemberExceptionHandler(ErrorCode errorCode) {super(errorCode);}
}
