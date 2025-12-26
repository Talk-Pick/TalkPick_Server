package talkPick.domain.today.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("TodayTopic 도메인 테스트")
class TodayTopicTest {

    @Test
    @DisplayName("of 메서드로 TodayTopic 생성 테스트")
    void of_메서드로_TodayTopic_생성_테스트() {
        // given
        Long memberId = 1L;
        Long topicId = 100L;

        // when
        TodayTopic todayTopic = TodayTopic.of(memberId, topicId);

        // then
        assertAll(
                () -> assertThat(todayTopic).isNotNull(),
                () -> assertThat(todayTopic.getMemberId()).isEqualTo(memberId),
                () -> assertThat(todayTopic.getTopicId()).isEqualTo(topicId)
        );
    }

    @Test
    @DisplayName("다양한 memberId와 topicId로 TodayTopic 생성 테스트")
    void 다양한_memberId와_topicId로_TodayTopic_생성_테스트() {
        // given
        Long[] memberIds = {1L, 100L, 999L};
        Long[] topicIds = {10L, 200L, 3000L};

        // when & then
        for (int i = 0; i < memberIds.length; i++) {
            Long memberId = memberIds[i];
            Long topicId = topicIds[i];
            TodayTopic todayTopic = TodayTopic.of(memberId, topicId);
            assertAll(
                    () -> assertThat(todayTopic.getMemberId()).isEqualTo(memberId),
                    () -> assertThat(todayTopic.getTopicId()).isEqualTo(topicId)
            );
        }
    }
}