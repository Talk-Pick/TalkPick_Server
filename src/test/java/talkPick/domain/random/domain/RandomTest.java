package talkPick.domain.random.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import talkPick.domain.random.adapter.in.dto.RandomReqDTO;
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
}