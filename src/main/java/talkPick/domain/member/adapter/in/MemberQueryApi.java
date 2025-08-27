package talkPick.domain.member.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import talkPick.domain.member.adapter.out.dto.MemberResDto;
import talkPick.global.response.CursorPageResponse;
import talkPick.global.response.ResultResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequestMapping("/api/v1/members")
@Tag(name = "유저 API", description = "유저 관련 API 입니다.")
public interface MemberQueryApi {
    @GetMapping("/liked-topics")
    @Operation(summary = "회원이 좋아요한 토픽 조회 API", description = "회원이 좋아요한 토픽들을 커서 페이징으로 조회하는 API입니다.")
    ResponseEntity<ResultResponse<CursorPageResponse<MemberResDto.MemberLikedTopicResDto>>> getMemberLikedTopics(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam(value = "cursor", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime cursor,
            @RequestParam(value = "size", defaultValue = "6") @Parameter(description = "페이지 크기 (1 이상)", schema = @Schema(minimum = "1")) int size
            );

    @GetMapping("/topic-results")
    @Operation(summary = "회원 토픽 결과 캘린더 조회 API", description = "특정 날짜의 회원 토픽 결과를 커서 페이징으로 조회하는 API입니다.")
    ResponseEntity<ResultResponse<CursorPageResponse<MemberResDto.MemberTopicResultResDto>>> getMemberTopicResults(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @Parameter(description = "조회할 날짜 (yyyy-MM-dd)") @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(value = "cursor", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime cursor,
            @RequestParam(value = "size", defaultValue = "6") @Parameter(description = "페이지 크기 (1 이상)", schema = @Schema(minimum = "1")) int size
            );

    @GetMapping("/me")
    @Operation(summary = "마이페이지 프로필 조회 API", description = "회원의 프로필 정보와 통계를 조회하는 API입니다.")
    ResponseEntity<ResultResponse<MemberResDto.ProfileResponse>> getProfile(
            @RequestHeader(value = "Authorization", required = false) String authorization);
}
