package talkPick.global.exception.handler;

import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

public class JwtExceptionHandler extends TalkPickException {
    public JwtExceptionHandler(ErrorCode errorCode) {super(errorCode);}
}
