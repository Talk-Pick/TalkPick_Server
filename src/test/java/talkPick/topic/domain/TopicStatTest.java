package talkPick.topic.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import talkPick.domain.topic.domain.TopicStat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("TopicStat 도메인 테스트")
class TopicStatTest {

    @Test
    @DisplayName("of 메서드로 TopicStat 생성 테스트")
    void of_메서드로_TopicStat_생성_테스트() {
        // given
        Long topicId = 100L;

        // when
        TopicStat topicStat = TopicStat.of(topicId);

        // then
        assertAll(
                () -> assertThat(topicStat).isNotNull(),
                () -> assertThat(topicStat.getTopicId()).isEqualTo(topicId),
                () -> assertThat(topicStat.getECount()).isEqualTo(0),
                () -> assertThat(topicStat.getICount()).isEqualTo(0),
                () -> assertThat(topicStat.getSCount()).isEqualTo(0),
                () -> assertThat(topicStat.getNCount()).isEqualTo(0),
                () -> assertThat(topicStat.getFCount()).isEqualTo(0),
                () -> assertThat(topicStat.getTCount()).isEqualTo(0),
                () -> assertThat(topicStat.getJCount()).isEqualTo(0),
                () -> assertThat(topicStat.getPCount()).isEqualTo(0),
                () -> assertThat(topicStat.getLikeCount()).isEqualTo(0),
                () -> assertThat(topicStat.getTeenCount()).isEqualTo(0),
                () -> assertThat(topicStat.getTwentiesCount()).isEqualTo(0),
                () -> assertThat(topicStat.getThirtiesCount()).isEqualTo(0),
                () -> assertThat(topicStat.getFortiesCount()).isEqualTo(0),
                () -> assertThat(topicStat.getFiftiesCount()).isEqualTo(0),
                () -> assertThat(topicStat.getMaleCount()).isEqualTo(0),
                () -> assertThat(topicStat.getFemaleCount()).isEqualTo(0),
                () -> assertThat(topicStat.getSelectCount()).isEqualTo(0),
                () -> assertThat(topicStat.getAverageTalkTime()).isEqualTo(0L)
        );
    }

    @Test
    @DisplayName("다양한 topicId로 TopicStat 생성 테스트")
    void 다양한_topicId로_TopicStat_생성_테스트() {
        // given
        Long[] topicIds = {1L, 100L, 999L, 12345L};

        // when & then
        for (Long topicId : topicIds) {
            TopicStat topicStat = TopicStat.of(topicId);
            assertThat(topicStat.getTopicId()).isEqualTo(topicId);
        }
    }

    @Test
    @DisplayName("TopicStat 생성 시 모든 카운트 0 초기화 테스트")
    void TopicStat_생성시_모든_카운트_0_초기화_테스트() {
        // given
        Long topicId = 100L;

        // when
        TopicStat topicStat = TopicStat.of(topicId);

        // then
        assertAll(
                () -> assertThat(topicStat.getLikeCount()).isZero(),
                () -> assertThat(topicStat.getSelectCount()).isZero(),
                () -> assertThat(topicStat.getAverageTalkTime()).isZero()
        );
    }
}