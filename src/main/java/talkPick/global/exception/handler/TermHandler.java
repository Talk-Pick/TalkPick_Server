package talkPick.global.exception.handler;

import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

public class TermHandler extends TalkPickException {
    public TermHandler(ErrorCode errorCode) {super(errorCode);}
}
