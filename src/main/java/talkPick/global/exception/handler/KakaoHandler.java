package talkPick.global.exception.handler;

import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

public class KakaoHandler extends TalkPickException {
    public KakaoHandler(ErrorCode errorCode) {
        super(errorCode);
    }
}
