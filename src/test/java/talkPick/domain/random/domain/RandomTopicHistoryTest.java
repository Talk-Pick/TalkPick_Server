package talkPick.domain.random.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import talkPick.domain.random.adapter.in.dto.RandomReqDTO;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("RandomTopicHistory 도메인 테스트")
class RandomTopicHistoryTest {

    @Test
    @DisplayName("of 메서드로 RandomTopicHistory 생성 테스트")
    void of_메서드로_RandomTopicHistory_생성_테스트() {
        // given
        Long memberId = 1L;
        Long randomId = 100L;
        RandomReqDTO.Record recordDTO = new RandomReqDTO.Record(200L, 1);

        // when
        RandomTopicHistory history = RandomTopicHistory.of(memberId, randomId, recordDTO);

        // then
        assertAll(
                () -> assertThat(history).isNotNull(),
                () -> assertThat(history.getMemberId()).isEqualTo(memberId),
                () -> assertThat(history.getRandomId()).isEqualTo(randomId),
                () -> assertThat(history.getTopicId()).isEqualTo(200L),
                () -> assertThat(history.getOrder()).isEqualTo(1),
                () -> assertThat(history.getStartAt()).isNotNull(),
                () -> assertThat(history.getEndAt()).isNull()
        );
    }

    @Test
    @DisplayName("ofRecord 메서드로 RandomTopicHistory 생성 테스트")
    void ofRecord_메서드로_RandomTopicHistory_생성_테스트() {
        // given
        Long memberId = 1L;
        Long randomId = 100L;
        LocalDateTime startAt = LocalDateTime.now().minusMinutes(10);
        LocalDateTime endAt = LocalDateTime.now();
        RandomReqDTO.TotalRecord totalRecordDTO = new RandomReqDTO.TotalRecord(
                200L, 1, startAt, endAt
        );

        // when
        RandomTopicHistory history = RandomTopicHistory.ofRecord(memberId, randomId, totalRecordDTO);

        // then
        assertAll(
                () -> assertThat(history).isNotNull(),
                () -> assertThat(history.getMemberId()).isEqualTo(memberId),
                () -> assertThat(history.getRandomId()).isEqualTo(randomId),
                () -> assertThat(history.getTopicId()).isEqualTo(200L),
                () -> assertThat(history.getOrder()).isEqualTo(1),
                () -> assertThat(history.getStartAt()).isEqualTo(startAt),
                () -> assertThat(history.getEndAt()).isEqualTo(endAt)
        );
    }

    @Test
    @DisplayName("next 호출 시 endAt 설정 테스트")
    void next_호출시_endAt_설정_테스트() {
        // given
        RandomReqDTO.Record recordDTO = new RandomReqDTO.Record(200L, 1);
        RandomTopicHistory history = RandomTopicHistory.of(1L, 100L, recordDTO);
        assertThat(history.getEndAt()).isNull();

        // when
        history.next();

        // then
        assertThat(history.getEndAt()).isNotNull();
    }

    @Test
    @DisplayName("다양한 order로 RandomTopicHistory 생성 테스트")
    void 다양한_order로_RandomTopicHistory_생성_테스트() {
        // given
        Long memberId = 1L;
        Long randomId = 100L;
        Integer[] orders = {1, 2, 3, 5, 10};

        // when & then
        for (Integer order : orders) {
            RandomReqDTO.Record recordDTO = new RandomReqDTO.Record(200L, order);
            RandomTopicHistory history = RandomTopicHistory.of(memberId, randomId, recordDTO);
            assertThat(history.getOrder()).isEqualTo(order);
        }
    }
}