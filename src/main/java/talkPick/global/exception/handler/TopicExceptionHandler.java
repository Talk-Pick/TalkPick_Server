package talkPick.global.exception.handler;

import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

public class TopicExceptionHandler extends TalkPickException {
    public TopicExceptionHandler(ErrorCode errorCode) {super(errorCode);}
}