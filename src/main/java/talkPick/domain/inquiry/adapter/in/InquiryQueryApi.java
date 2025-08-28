package talkPick.domain.inquiry.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import talkPick.domain.inquiry.adapter.out.dto.InquiryResDto;
import talkPick.global.response.CursorPageResponse;

import java.time.LocalDateTime;

@RequestMapping("/api/v1/inquiry")
@Tag(name = "문의 조회 API", description = "회원이 등록한 문의 목록 조회 API")
public interface InquiryQueryApi {
    @GetMapping
    @Operation(summary = "내 문의 목록 조회 API", description = "회원이 등록한 문의들을 커서 페이징으로 조회합니다.")
    CursorPageResponse<InquiryResDto.InquiryListItemResDto> getMyInquiries(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam(value = "cursor", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime cursor,
            @RequestParam(value = "size", defaultValue = "6") @Parameter(description = "페이지 크기 (1 이상)", schema = @Schema(minimum = "1")) int size
    );
}
