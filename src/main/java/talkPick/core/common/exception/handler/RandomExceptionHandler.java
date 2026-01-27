package talkPick.core.common.exception.handler;

import talkPick.core.common.exception.ErrorCode;
import talkPick.core.common.exception.TalkPickException;

public class RandomExceptionHandler extends TalkPickException {
    public RandomExceptionHandler(ErrorCode errorCode) {super(errorCode);}
}