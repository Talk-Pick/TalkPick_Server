package talkPick.core.common.exception.handler;

import talkPick.core.common.exception.ErrorCode;
import talkPick.core.common.exception.TalkPickException;

public class GoogleHandler extends TalkPickException {
    public GoogleHandler(ErrorCode errorCode) {
        super(errorCode);
    }
}
