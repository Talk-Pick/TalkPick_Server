package talkPick.domain.topic.domain.event;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("TopicLikedEvent 도메인 이벤트 테스트")
class TopicLikedEventTest {

    @Test
    @DisplayName("of 메서드로 TopicLikedEvent 생성 테스트")
    void of_메서드로_TopicLikedEvent_생성_테스트() {
        // given
        Object source = this;
        Long memberId = 1L;
        Long topicId = 100L;

        // when
        TopicLikedEvent event = TopicLikedEvent.of(source, memberId, topicId);

        // then
        assertAll(
                () -> assertThat(event).isNotNull(),
                () -> assertThat(event.getMemberId()).isEqualTo(memberId),
                () -> assertThat(event.getTopicId()).isEqualTo(topicId),
                () -> assertThat(event.getSource()).isEqualTo(source)
        );
    }

    @Test
    @DisplayName("다양한 memberId와 topicId로 TopicLikedEvent 생성 테스트")
    void 다양한_memberId와_topicId로_TopicLikedEvent_생성_테스트() {
        // given
        Object source = this;
        Long[] memberIds = {1L, 100L, 999L};
        Long[] topicIds = {10L, 200L, 3000L};

        // when & then
        for (int i = 0; i < memberIds.length; i++) {
            Long memberId = memberIds[i];
            Long topicId = topicIds[i];
            TopicLikedEvent event = TopicLikedEvent.of(source, memberId, topicId);
            assertAll(
                    () -> assertThat(event.getMemberId()).isEqualTo(memberId),
                    () -> assertThat(event.getTopicId()).isEqualTo(topicId)
            );
        }
    }
}