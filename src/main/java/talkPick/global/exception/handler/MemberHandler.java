package talkPick.global.exception.handler;

import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

public class MemberHandler extends TalkPickException {
    public MemberHandler(ErrorCode errorCode) {super(errorCode);}
}
