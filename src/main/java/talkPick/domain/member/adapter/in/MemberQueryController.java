package talkPick.domain.member.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import talkPick.domain.member.dto.*;
import talkPick.domain.member.application.MemberQueryService;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.port.in.MemberQueryUseCase;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import talkPick.global.security.jwt.util.JwtProvider;
import talkPick.global.util.CookieUtil;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class MemberQueryController implements MemberQueryApi {
    private final MemberQueryUseCase memberQueryUseCase;
    private final JwtProvider jwtProvider;
    private final CookieUtil cookieUtil;
    private final MemberQueryService memberQueryService;


    @GetMapping("/members/me")
    @Operation(summary = "마이페이지 프로필 조회 API", description = "회원의 프로필 정보와 통계를 조회하는 API입니다.")
    public ResponseEntity<MemberResDto.ProfileResponse> getProfile(
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        return ResponseEntity.ok(memberQueryUseCase.getProfile(authorization));
    }

    @GetMapping("/members/me/liked-topics")
    @ResponseBody
    public Page<MemberResDto.MemberLikedTopicsResDto> getMemberLikedTopics(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long memberId = Long.parseLong(authentication.getName()); // JWT에서 추출된 사용자 ID
        Page<MemberResDto.MemberLikedTopicsResDto> memberLikedTopics = memberQueryUseCase.getMemberLikedTopics(memberId, pageable);
        return memberLikedTopics;
    }


    //멤버 캘린더 토픽 결과 조회
    @GetMapping("/members/topic/Results")
    @ResponseBody
    public Page<MemberResDto.MemberTopicResultResDto> getMemberTopicResults(@RequestParam("date") LocalDate date, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long memberId = Long.parseLong(authentication.getName());
        Page<MemberResDto.MemberTopicResultResDto> memberTopicResults = memberQueryUseCase.getMemberTopicResultsByCreatedDate(memberId, date, pageable);
        return memberTopicResults;
    }
}
