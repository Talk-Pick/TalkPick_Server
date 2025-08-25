package talkPick.global.exception.handler;

import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

public class KakaoExceptionHandler extends TalkPickException {
    public KakaoExceptionHandler(ErrorCode errorCode) {
        super(errorCode);
    }
}
