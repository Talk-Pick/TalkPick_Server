package talkPick.domain.notice.domain.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class NoticeReadEvent extends ApplicationEvent {
    private final Long noticeId;

    private NoticeReadEvent(Object source, Long noticeId) {
        super(source);
        this.noticeId = noticeId;
    }

    public static NoticeReadEvent of(Object source, Long noticeId) {
        return new NoticeReadEvent(source, noticeId);
    }
}