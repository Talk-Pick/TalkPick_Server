package talkPick.domain.member.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import talkPick.domain.member.dto.*;
import talkPick.domain.member.port.in.MemberQueryUseCase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import talkPick.global.response.CursorPageResponse;
import talkPick.global.response.ResultResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/members")
public class MemberQueryController implements MemberQueryApi {
    private final MemberQueryUseCase memberQueryUseCase;

    @Override
    @GetMapping("/me")
    @Operation(summary = "마이페이지 프로필 조회 API", description = "회원의 프로필 정보와 통계를 조회하는 API입니다.")
    public ResponseEntity<ResultResponse<MemberResDto.ProfileResponse>> getProfile(
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        return ResponseEntity.ok(ResultResponse.success(memberQueryUseCase.getProfile(authorization)));
    }

    // 멤버 좋아요 누른 토픽 조회
    @Override
    @GetMapping("/liked-topics")
    @ResponseBody
    public ResponseEntity<ResultResponse<CursorPageResponse<MemberResDto.MemberLikedTopicResDto>>> getMemberLikedTopics(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam(value = "cursor", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime cursor,
            @RequestParam(value = "size", defaultValue = "6") @Parameter(description = "페이지 크기 (1 이상)", schema = @Schema(minimum = "1")) int size) {
        return ResponseEntity.ok(ResultResponse.success(memberQueryUseCase.getMemberLikedTopics(authorization, cursor, size);))
    }


    //멤버 캘린더 토픽 결과 조회
    @Override
    @GetMapping("/topic-results")
    @ResponseBody
    public Page<MemberResDto.MemberTopicResultResDto> getMemberTopicResults(@RequestParam("date") LocalDate date, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long memberId = Long.parseLong(authentication.getName());
        Page<MemberResDto.MemberTopicResultResDto> memberTopicResults = memberQueryUseCase.getMemberTopicResultsByCreatedDate(memberId, date, pageable);
        return memberTopicResults;
    }
}
