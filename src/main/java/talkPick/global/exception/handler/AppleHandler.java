package talkPick.global.exception.handler;

import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

public class AppleHandler extends TalkPickException {
    public AppleHandler(ErrorCode errorCode) {
        super(errorCode);
    }

    public AppleHandler(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}