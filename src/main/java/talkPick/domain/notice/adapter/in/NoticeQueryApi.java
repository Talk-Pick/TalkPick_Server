package talkPick.domain.notice.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import talkPick.domain.notice.adapter.in.dto.NoticeReqDTO;
import talkPick.domain.notice.adapter.out.dto.NoticeResDTO;
import talkPick.global.response.CursorPageResponse;

@RequestMapping("/api/v1/notice")
@Tag(name = "공지사항 API", description = "공지사항 관련 API 입니다.")
public interface NoticeQueryApi {
    @GetMapping("/")
    @Operation(summary = "공지사항 전체 조회", description = "공지사항 전체 조회 API 입니다.")
    CursorPageResponse<NoticeResDTO.NoticeSummary> getNotices(NoticeReqDTO.Cursor cursor);

    @GetMapping("/{id}")
    @Operation(summary = "공지사항 상세 조회", description = "공지사항 상세 조회 API 입니다.")
    NoticeResDTO.NoticeDetail getNoticeDetail(@PathVariable("id") Long noticeId);
}
