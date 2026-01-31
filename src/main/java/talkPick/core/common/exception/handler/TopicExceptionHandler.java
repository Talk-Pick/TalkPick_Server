package talkPick.core.common.exception.handler;

import talkPick.core.common.exception.ErrorCode;
import talkPick.core.common.exception.TalkPickException;

public class TopicExceptionHandler extends TalkPickException {
    public TopicExceptionHandler(ErrorCode errorCode) {super(errorCode);}
}