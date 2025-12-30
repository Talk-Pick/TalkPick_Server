package talkPick.random.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import talkPick.domain.random.adapter.in.dto.RandomReqDTO;
import talkPick.domain.random.domain.Random;
import talkPick.domain.random.domain.type.RandomType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Random 도메인 테스트")
class RandomTest {

    @Test
    @DisplayName("from 메서드로 Random 생성 테스트")
    void from_메서드로_Random_생성_테스트() {
        // given
        Long memberId = 1L;

        // when
        Random random = Random.from(memberId);

        // then
        assertAll(
                () -> assertThat(random).isNotNull(),
                () -> assertThat(random.getMemberId()).isEqualTo(memberId),
                () -> assertThat(random.getType()).isEqualTo(RandomType.START),
                () -> assertThat(random.getOneLine()).isNull(),
                () -> assertThat(random.getRating()).isNull()
        );
    }

    @Test
    @DisplayName("quit 호출 시 RandomType이 QUIT으로 변경 테스트")
    void quit_호출시_RandomType_QUIT으로_변경_테스트() {
        // given
        Random random = Random.from(1L);

        // when
        random.quit();

        // then
        assertThat(random.getType()).isEqualTo(RandomType.QUIT);
    }

    @Test
    @DisplayName("end 호출 시 RandomType이 COMPLETED로 변경 테스트")
    void end_호출시_RandomType_COMPLETED로_변경_테스트() {
        // given
        Random random = Random.from(1L);

        // when
        random.end();

        // then
        assertThat(random.getType()).isEqualTo(RandomType.COMPLETED);
    }

    @Test
    @DisplayName("rate 호출 시 평점 설정 테스트")
    void rate_호출시_평점_설정_테스트() {
        // given
        Random random = Random.from(1L);
        RandomReqDTO.Rate rateDTO = new RandomReqDTO.Rate(5);

        // when
        random.rate(rateDTO);

        // then
        assertThat(random.getRating()).isEqualTo(5);
    }

    @Test
    @DisplayName("comment 호출 시 한 줄 평 설정 테스트")
    void comment_호출시_한줄평_설정_테스트() {
        // given
        Random random = Random.from(1L);
        String oneLine = "정말 재밌었어요!";
        RandomReqDTO.Comment commentDTO = new RandomReqDTO.Comment(oneLine);

        // when
        random.comment(commentDTO);

        // then
        assertThat(random.getOneLine()).isEqualTo(oneLine);
    }

    @Test
    @DisplayName("rate와 comment 호출 시 모두 설정 테스트")
    void rate와_comment_호출시_모두_설정_테스트() {
        // given
        Random random = Random.from(1L);
        RandomReqDTO.Rate rateDTO = new RandomReqDTO.Rate(4);
        RandomReqDTO.Comment commentDTO = new RandomReqDTO.Comment("좋아요");

        // when
        random.rate(rateDTO);
        random.comment(commentDTO);

        // then
        assertAll(
                () -> assertThat(random.getRating()).isEqualTo(4),
                () -> assertThat(random.getOneLine()).isEqualTo("좋아요")
        );
    }

    @Test
    @DisplayName("START 상태에서 QUIT으로 상태 전이 테스트")
    void START_상태에서_QUIT으로_상태_전이_테스트() {
        // given
        Random random = Random.from(1L);
        assertThat(random.getType()).isEqualTo(RandomType.START);

        // when
        random.quit();

        // then
        assertThat(random.getType()).isEqualTo(RandomType.QUIT);
    }

    @Test
    @DisplayName("START 상태에서 COMPLETED로 상태 전이 테스트")
    void START_상태에서_COMPLETED로_상태_전이_테스트() {
        // given
        Random random = Random.from(1L);
        assertThat(random.getType()).isEqualTo(RandomType.START);

        // when
        random.end();

        // then
        assertThat(random.getType()).isEqualTo(RandomType.COMPLETED);
    }

    @Test
    @DisplayName("다양한 평점 값으로 rate 호출 테스트")
    void 다양한_평점_값으로_rate_호출_테스트() {
        // given
        Integer[] ratings = {1, 2, 3, 4, 5};

        // when & then
        for (Integer rating : ratings) {
            Random random = Random.from(1L);
            RandomReqDTO.Rate rateDTO = new RandomReqDTO.Rate(rating);
            random.rate(rateDTO);
            assertThat(random.getRating()).isEqualTo(rating);
        }
    }

    @Test
    @DisplayName("다양한 memberId로 Random 생성 테스트")
    void 다양한_memberId로_Random_생성_테스트() {
        // given
        Long[] memberIds = {1L, 100L, 999L, 12345L};

        // when & then
        for (Long memberId : memberIds) {
            Random random = Random.from(memberId);
            assertThat(random.getMemberId()).isEqualTo(memberId);
        }
    }
}