package talkPick.core.common.exception.handler;

import talkPick.core.common.exception.ErrorCode;
import talkPick.core.common.exception.TalkPickException;

public class JwtExceptionHandler extends TalkPickException {
    public JwtExceptionHandler(ErrorCode errorCode) {super(errorCode);}
}
