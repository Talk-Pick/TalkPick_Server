package talkPick.core.common.exception.handler;

import talkPick.core.common.exception.ErrorCode;
import talkPick.core.common.exception.TalkPickException;

public class MemberExceptionHandler extends TalkPickException {
    public MemberExceptionHandler(ErrorCode errorCode) {super(errorCode);}
}
