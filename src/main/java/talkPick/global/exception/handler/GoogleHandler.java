package talkPick.global.exception.handler;

import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

public class GoogleHandler extends TalkPickException {
    public GoogleHandler(ErrorCode errorCode) {
        super(errorCode);
    }
}
