package talkPick.core.common.exception.handler;

import talkPick.core.common.exception.ErrorCode;
import talkPick.core.common.exception.TalkPickException;

public class TermExceptionHandler extends TalkPickException {
    public TermExceptionHandler(ErrorCode errorCode) {super(errorCode);}
}
