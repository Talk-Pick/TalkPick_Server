package talkPick.notice.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import talkPick.domain.notice.adapter.in.dto.NoticeReqDTO;
import talkPick.domain.notice.adapter.out.dto.NoticeResDTO;
import talkPick.domain.notice.application.NoticeQueryService;
import talkPick.domain.notice.domain.event.NoticeReadEvent;
import talkPick.domain.notice.port.out.NoticeQueryRepositoryPort;
import talkPick.global.response.CursorPageResponse;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@DisplayName("NoticeQueryService 테스트")
class NoticeQueryServiceTest {

    @InjectMocks
    private NoticeQueryService noticeQueryService;

    @Mock
    private NoticeQueryRepositoryPort noticeQueryRepositoryPort;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Test
    @DisplayName("커서 기반 페이징으로 공지사항 목록 조회 테스트")
    void 커서_기반_페이징으로_공지사항_목록_조회_테스트() {
        // given
        NoticeReqDTO.Cursor cursor = new NoticeReqDTO.Cursor(
                LocalDateTime.now(),
                100L,
                20
        );

        List<NoticeResDTO.NoticeSummary> noticeList = List.of(
                new NoticeResDTO.NoticeSummary(1L, "제목1", "내용1", LocalDateTime.now(), LocalDateTime.now()),
                new NoticeResDTO.NoticeSummary(2L, "제목2", "내용2", LocalDateTime.now(), LocalDateTime.now())
        );

        CursorPageResponse<NoticeResDTO.NoticeSummary> expectedResponse = CursorPageResponse.<NoticeResDTO.NoticeSummary>builder()
                .items(noticeList)
                .hasNext(true)
                .build();

        given(noticeQueryRepositoryPort.findNoticesWithCursor(cursor))
                .willReturn(expectedResponse);

        // when
        CursorPageResponse<NoticeResDTO.NoticeSummary> response = noticeQueryService.getNotices(cursor);

        // then
        assertAll(
                () -> assertThat(response).isNotNull(),
                () -> assertThat(response.getItems()).hasSize(2),
                () -> assertThat(response.isHasNext()).isTrue(),
                () -> verify(noticeQueryRepositoryPort, times(1)).findNoticesWithCursor(cursor)
        );
    }

    @Test
    @DisplayName("공지사항 목록 조회 시 빈 결과 반환 테스트")
    void 공지사항_목록_조회시_빈_결과_반환_테스트() {
        // given
        NoticeReqDTO.Cursor cursor = new NoticeReqDTO.Cursor(
                LocalDateTime.now(),
                1L,
                20
        );

        CursorPageResponse<NoticeResDTO.NoticeSummary> emptyResponse = CursorPageResponse.<NoticeResDTO.NoticeSummary>builder()
                .items(Collections.emptyList())
                .hasNext(false)
                .build();

        given(noticeQueryRepositoryPort.findNoticesWithCursor(cursor))
                .willReturn(emptyResponse);

        // when
        CursorPageResponse<NoticeResDTO.NoticeSummary> response = noticeQueryService.getNotices(cursor);

        // then
        assertAll(
                () -> assertThat(response).isNotNull(),
                () -> assertThat(response.getItems()).isEmpty(),
                () -> assertThat(response.isHasNext()).isFalse(),
                () -> verify(noticeQueryRepositoryPort, times(1)).findNoticesWithCursor(cursor)
        );
    }

    @Test
    @DisplayName("공지사항 상세 조회 및 조회 이벤트 발행 테스트")
    void 공지사항_상세_조회_및_조회_이벤트_발행_테스트() {
        // given
        Long noticeId = 100L;
        NoticeResDTO.NoticeDetail expectedDetail = new NoticeResDTO.NoticeDetail(
                noticeId,
                "공지사항 제목",
                "공지사항 내용입니다.",
                50,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        expectedDetail.addImageUrls(List.of(
                "https://example.com/image1.png",
                "https://example.com/image2.png"
        ));

        given(noticeQueryRepositoryPort.findNoticeDetailById(noticeId))
                .willReturn(expectedDetail);

        // when
        NoticeResDTO.NoticeDetail result = noticeQueryService.getNoticeDetail(noticeId);

        // then
        assertAll(
                () -> assertThat(result).isNotNull(),
                () -> assertThat(result.getNoticeId()).isEqualTo(noticeId),
                () -> assertThat(result.getTitle()).isEqualTo("공지사항 제목"),
                () -> assertThat(result.getContent()).isEqualTo("공지사항 내용입니다."),
                () -> assertThat(result.getReadCount()).isEqualTo(50),
                () -> assertThat(result.getImageUrls()).hasSize(2),
                () -> verify(noticeQueryRepositoryPort, times(1)).findNoticeDetailById(noticeId),
                () -> verify(eventPublisher, times(1)).publishEvent(any(NoticeReadEvent.class))
        );
    }

    @Test
    @DisplayName("이미지가 없는 공지사항 상세 조회 테스트")
    void 이미지가_없는_공지사항_상세_조회_테스트() {
        // given
        Long noticeId = 200L;
        NoticeResDTO.NoticeDetail expectedDetail = new NoticeResDTO.NoticeDetail(
                noticeId,
                "이미지 없는 공지",
                "내용",
                10,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        expectedDetail.addImageUrls(Collections.emptyList());

        given(noticeQueryRepositoryPort.findNoticeDetailById(noticeId))
                .willReturn(expectedDetail);

        // when
        NoticeResDTO.NoticeDetail result = noticeQueryService.getNoticeDetail(noticeId);

        // then
        assertAll(
                () -> assertThat(result).isNotNull(),
                () -> assertThat(result.getImageUrls()).isEmpty(),
                () -> verify(eventPublisher, times(1)).publishEvent(any(NoticeReadEvent.class))
        );
    }

    @Test
    @DisplayName("공지사항 상세 조회 시 Repository 조회 후 이벤트 발행 순서 확인 테스트")
    void 공지사항_상세_조회시_Repository_조회_후_이벤트_발행_순서_확인_테스트() {
        // given
        Long noticeId = 300L;
        NoticeResDTO.NoticeDetail mockDetail = new NoticeResDTO.NoticeDetail(
                noticeId,
                "제목",
                "내용",
                0,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        given(noticeQueryRepositoryPort.findNoticeDetailById(noticeId))
                .willReturn(mockDetail);

        // when
        noticeQueryService.getNoticeDetail(noticeId);

        // then
        var inOrder = org.mockito.Mockito.inOrder(noticeQueryRepositoryPort, eventPublisher);
        inOrder.verify(noticeQueryRepositoryPort).findNoticeDetailById(noticeId);
        inOrder.verify(eventPublisher).publishEvent(any(NoticeReadEvent.class));
    }

    @Test
    @DisplayName("공지사항 목록 조회 시 Repository 예외 발생 테스트")
    void 공지사항_목록_조회시_Repository_예외_발생_테스트() {
        // given
        NoticeReqDTO.Cursor cursor = new NoticeReqDTO.Cursor(
                LocalDateTime.now(),
                1L,
                20
        );

        given(noticeQueryRepositoryPort.findNoticesWithCursor(cursor))
                .willThrow(new RuntimeException("DB Connection failed"));

        // when & then
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class,
                () -> noticeQueryService.getNotices(cursor));
    }

    @Test
    @DisplayName("공지사항 상세 조회 시 Repository 예외 발생 테스트")
    void 공지사항_상세_조회시_Repository_예외_발생_테스트() {
        // given
        Long noticeId = -1L;

        given(noticeQueryRepositoryPort.findNoticeDetailById(noticeId))
                .willThrow(new IllegalArgumentException("Notice not found"));

        // when & then
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                () -> noticeQueryService.getNoticeDetail(noticeId));
    }
}