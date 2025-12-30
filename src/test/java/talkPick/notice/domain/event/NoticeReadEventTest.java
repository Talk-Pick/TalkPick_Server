package talkPick.notice.domain.event;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import talkPick.domain.notice.domain.event.NoticeReadEvent;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("NoticeReadEvent 도메인 이벤트 테스트")
class NoticeReadEventTest {

    @Test
    @DisplayName("of 메서드로 NoticeReadEvent 생성 테스트")
    void of_메서드로_NoticeReadEvent_생성_테스트() {
        // given
        Object source = this;
        Long noticeId = 100L;

        // when
        NoticeReadEvent event = NoticeReadEvent.of(source, noticeId);

        // then
        assertAll(
                () -> assertThat(event).isNotNull(),
                () -> assertThat(event.getNoticeId()).isEqualTo(noticeId),
                () -> assertThat(event.getSource()).isEqualTo(source)
        );
    }

    @Test
    @DisplayName("다양한 noticeId로 NoticeReadEvent 생성 테스트")
    void 다양한_noticeId로_NoticeReadEvent_생성_테스트() {
        // given
        Object source = this;
        Long[] noticeIds = {1L, 999L, 123456L};

        // when & then
        for (Long noticeId : noticeIds) {
            NoticeReadEvent event = NoticeReadEvent.of(source, noticeId);
            assertThat(event.getNoticeId()).isEqualTo(noticeId);
        }
    }
}