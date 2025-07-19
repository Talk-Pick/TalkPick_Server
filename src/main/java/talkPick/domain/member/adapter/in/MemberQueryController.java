package talkPick.domain.member.adapter.in;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import talkPick.domain.member.adapter.in.dto.MemberDetailResDto;
import talkPick.domain.member.adapter.in.dto.MemberLikedTopicsResDto;
import talkPick.domain.member.adapter.in.dto.MemberTopicResultResDto;
import talkPick.domain.member.adapter.out.dto.MemberEmailResDTO;
import talkPick.domain.member.adapter.out.dto.MemberKakaoResDTO;
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

    //이메일 가입 회원 조회
    @GetMapping("/members/email")
    @ResponseBody
    public List<MemberEmailResDTO> getEmailMembers() {
        List<MemberEmailResDTO> memberEmailResDtoList = memberQueryUseCase.getEmailMembers();
        return memberEmailResDtoList;
    }

    //카카오 가입 회원 조회
    @GetMapping("/members/kakao")
    @ResponseBody
    public List<MemberKakaoResDTO> getKakaoMembers() {
        List<MemberKakaoResDTO> memberKakaoResDTOList = memberQueryUseCase.getkakaoMembers();
        return memberKakaoResDTOList;
    }

    @GetMapping("/members/me")
    @ResponseBody
    public MemberDetailResDto getMemberInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long memberId = Long.parseLong(authentication.getName()); // JWT에서 추출된 사용자 ID

        MemberDetailResDto memberInfo = memberQueryUseCase.getMemberInfo(memberId);
        return memberInfo;
    }

    @GetMapping("/members/me/liked-topics")
    @ResponseBody
    public Page<MemberLikedTopicsResDto> getMemberLikedTopics(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long memberId = Long.parseLong(authentication.getName()); // JWT에서 추출된 사용자 ID
        Page<MemberLikedTopicsResDto> memberLikedTopics = memberQueryUseCase.getMemberLikedTopics(memberId, pageable);
        return memberLikedTopics;
    }


    //멤버 캘린더 토픽 결과 조회
    @GetMapping("/members/topic/Results")
    @ResponseBody
    public Page<MemberTopicResultResDto> getMemberTopicResults(@RequestParam("date") LocalDate date, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long memberId = Long.parseLong(authentication.getName());
        Page<MemberTopicResultResDto> memberTopicResults = memberQueryUseCase.getMemberTopicResultsByCreatedDate(memberId, date, pageable);
        return memberTopicResults;
    }

    /**
     * 로그인 이후 화면 (홈화면)에서 사용자 정보를 조회합니다.
     */
    @GetMapping("/topic")
    @Tag(name = "홈 화면")
    public MemberKakaoResDTO getUserInfoFromToken(HttpServletRequest request) {
        String accessToken = cookieUtil.getCookieValue(request, "access_token");

        if (accessToken == null) {
            log.error("액세스 토큰이 없습니다.");
            return null;
        }

        try {
            // 토큰에서 사용자 ID 추출
            Long memberId = jwtProvider.getUserIdFromToken(accessToken);

            // 사용자 정보 조회
            Optional<Member> memberOpt = memberQueryService.findById(memberId);
            return memberOpt.map(MemberKakaoResDTO::new).orElse(null);
        } catch (Exception e) {
            log.error("토큰 처리 중 오류 발생: {}", e.getMessage(), e);
            return null;
        }
    }
}
