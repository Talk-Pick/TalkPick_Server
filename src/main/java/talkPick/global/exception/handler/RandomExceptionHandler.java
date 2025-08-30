package talkPick.global.exception.handler;

import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

public class RandomExceptionHandler extends TalkPickException {
    public RandomExceptionHandler(ErrorCode errorCode) {super(errorCode);}
}