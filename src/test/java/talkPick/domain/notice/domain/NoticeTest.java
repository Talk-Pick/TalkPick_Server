package talkPick.domain.notice.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import talkPick.global.model.TalkPickStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Notice 도메인 테스트")
class NoticeTest {

    @Test
    @DisplayName("of 메서드로 Notice 생성 테스트")
    void of_메서드로_Notice_생성_테스트() {
        // given
        Long adminId = 1L;
        String title = "공지사항 제목";
        String content = "공지사항 내용입니다.";
        Integer readCount = 0;
        TalkPickStatus status = TalkPickStatus.ACTIVE;

        // when
        Notice notice = Notice.of(adminId, title, content, readCount, status);

        // then
        assertAll(
                () -> assertThat(notice).isNotNull(),
                () -> assertThat(notice.getAdminId()).isEqualTo(adminId),
                () -> assertThat(notice.getTitle()).isEqualTo(title),
                () -> assertThat(notice.getContent()).isEqualTo(content),
                () -> assertThat(notice.getReadCount()).isEqualTo(readCount),
                () -> assertThat(notice.getStatus()).isEqualTo(status)
        );
    }

    @Test
    @DisplayName("plusReadCount 호출 시 조회수 1 증가 테스트")
    void plusReadCount_호출시_조회수_1_증가_테스트() {
        // given
        Notice notice = Notice.of(1L, "제목", "내용", 0, TalkPickStatus.ACTIVE);
        Integer initialReadCount = notice.getReadCount();

        // when
        notice.plusReadCount();

        // then
        assertThat(notice.getReadCount()).isEqualTo(initialReadCount + 1);
    }

    @Test
    @DisplayName("plusReadCount 여러 번 호출 시 조회수 누적 증가 테스트")
    void plusReadCount_여러번_호출시_조회수_누적_증가_테스트() {
        // given
        Notice notice = Notice.of(1L, "제목", "내용", 10, TalkPickStatus.ACTIVE);
        int incrementCount = 5;

        // when
        for (int i = 0; i < incrementCount; i++) {
            notice.plusReadCount();
        }

        // then
        assertThat(notice.getReadCount()).isEqualTo(15);
    }

    @Test
    @DisplayName("DIS_ACTIVE 상태로 Notice 생성 테스트")
    void DIS_ACTIVE_상태로_Notice_생성_테스트() {
        // given
        TalkPickStatus status = TalkPickStatus.DIS_ACTIVE;

        // when
        Notice notice = Notice.of(1L, "비활성 공지", "내용", 0, status);

        // then
        assertThat(notice.getStatus()).isEqualTo(TalkPickStatus.DIS_ACTIVE);
    }
}
