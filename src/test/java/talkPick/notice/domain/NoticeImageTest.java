package talkPick.notice.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import talkPick.domain.notice.domain.NoticeImage;
import talkPick.core.common.model.TalkPickStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("NoticeImage 도메인 테스트")
class NoticeImageTest {

    @Test
    @DisplayName("of 메서드로 NoticeImage 생성 테스트")
    void of_메서드로_NoticeImage_생성_테스트() {
        // given
        Long noticeId = 100L;
        String imageUrl = "https://example.com/notice-image.png";
        TalkPickStatus status = TalkPickStatus.ACTIVE;

        // when
        NoticeImage noticeImage = NoticeImage.of(noticeId, imageUrl, status);

        // then
        assertAll(
                () -> assertThat(noticeImage).isNotNull(),
                () -> assertThat(noticeImage.getNoticeId()).isEqualTo(noticeId),
                () -> assertThat(noticeImage.getImageUrl()).isEqualTo(imageUrl),
                () -> assertThat(noticeImage.getStatus()).isEqualTo(status)
        );
    }

    @Test
    @DisplayName("DIS_ACTIVE 상태로 NoticeImage 생성 테스트")
    void DIS_ACTIVE_상태로_NoticeImage_생성_테스트() {
        // given
        Long noticeId = 100L;
        String imageUrl = "https://example.com/deleted-image.png";
        TalkPickStatus status = TalkPickStatus.DIS_ACTIVE;

        // when
        NoticeImage noticeImage = NoticeImage.of(noticeId, imageUrl, status);

        // then
        assertThat(noticeImage.getStatus()).isEqualTo(TalkPickStatus.DIS_ACTIVE);
    }

    @Test
    @DisplayName("다양한 이미지 URL 형식으로 NoticeImage 생성 테스트")
    void 다양한_이미지_URL_형식으로_NoticeImage_생성_테스트() {
        // given
        Long noticeId = 100L;
        String[] imageUrls = {
                "https://cdn.example.com/images/notice/12345.jpg",
                "https://s3.amazonaws.com/bucket/notice-img.png",
                "https://example.com/path/to/image.webp"
        };

        // when & then
        for (String url : imageUrls) {
            NoticeImage noticeImage = NoticeImage.of(noticeId, url, TalkPickStatus.ACTIVE);
            assertThat(noticeImage.getImageUrl()).isEqualTo(url);
        }
    }
}