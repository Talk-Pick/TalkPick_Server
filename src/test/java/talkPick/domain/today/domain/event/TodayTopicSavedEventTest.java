package talkPick.domain.today.domain.event;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import talkPick.domain.today.domain.TodayTopic;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("TodayTopicSavedEvent 도메인 이벤트 테스트")
class TodayTopicSavedEventTest {

    @Test
    @DisplayName("of 메서드로 TodayTopicSavedEvent 생성 테스트")
    void of_메서드로_TodayTopicSavedEvent_생성_테스트() {
        // given
        Object source = this;
        List<TodayTopic> todayTopics = List.of(
                TodayTopic.of(1L, 100L),
                TodayTopic.of(1L, 200L)
        );

        // when
        TodayTopicSavedEvent event = TodayTopicSavedEvent.of(source, todayTopics);

        // then
        assertAll(
                () -> assertThat(event).isNotNull(),
                () -> assertThat(event.getTodayTopics()).hasSize(2),
                () -> assertThat(event.getTodayTopics()).isEqualTo(todayTopics),
                () -> assertThat(event.getSource()).isEqualTo(source)
        );
    }

    @Test
    @DisplayName("빈 리스트로 TodayTopicSavedEvent 생성 테스트")
    void 빈_리스트로_TodayTopicSavedEvent_생성_테스트() {
        // given
        Object source = this;
        List<TodayTopic> emptyList = Collections.emptyList();

        // when
        TodayTopicSavedEvent event = TodayTopicSavedEvent.of(source, emptyList);

        // then
        assertAll(
                () -> assertThat(event).isNotNull(),
                () -> assertThat(event.getTodayTopics()).isEmpty()
        );
    }

    @Test
    @DisplayName("여러 개의 TodayTopic으로 이벤트 생성 테스트")
    void 여러_개의_TodayTopic으로_이벤트_생성_테스트() {
        // given
        Object source = this;
        List<TodayTopic> todayTopics = List.of(
                TodayTopic.of(1L, 100L),
                TodayTopic.of(1L, 200L),
                TodayTopic.of(1L, 300L),
                TodayTopic.of(1L, 400L),
                TodayTopic.of(1L, 500L)
        );

        // when
        TodayTopicSavedEvent event = TodayTopicSavedEvent.of(source, todayTopics);

        // then
        assertThat(event.getTodayTopics()).hasSize(5);
    }
}