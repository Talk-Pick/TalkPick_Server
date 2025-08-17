package talkPick.domain.member.adapter.in;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import talkPick.domain.member.dto.*;
import talkPick.global.response.CursorPageResponse;
import talkPick.global.response.ResultResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Tag(name = "유저 API", description = "유저 관련 API 입니다.")
public interface MemberQueryApi {
    ResponseEntity<ResultResponse<CursorPageResponse<MemberResDto.MemberLikedTopicResDto>>> getMemberLikedTopics(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam(value = "cursor", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime cursor,
            @RequestParam(value = "size", defaultValue = "6") @Parameter(description = "페이지 크기 (1 이상)", schema = @Schema(minimum = "1")) int size
            );
    Page<MemberResDto.MemberTopicResultResDto> getMemberTopicResults(@RequestParam("date") LocalDate date, Pageable pageable);
    ResponseEntity<ResultResponse<MemberResDto.ProfileResponse>> getProfile(
            @RequestHeader(value = "Authorization", required = false) String authorization);
}
