package talkPick.domain.member.adapter.in;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import talkPick.domain.member.adapter.in.dto.MemberDetailResDto;
import talkPick.domain.member.adapter.in.dto.MemberLikedTopicsResDto;
import talkPick.domain.member.adapter.out.dto.MemberEmailResDTO;
import talkPick.domain.member.adapter.out.dto.MemberKakaoResDTO;
import talkPick.domain.member.application.MemberQueryService;
import talkPick.domain.member.port.in.MemberQueryUseCase;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class MemberQueryController implements MemberQueryApi {
    private final MemberQueryUseCase memberQueryUseCase;
    private final MemberQueryService memberQueryService;

    //이메일 가입 회원 조회
    @GetMapping("/members/email")
    @ResponseBody
    public List<MemberEmailResDTO> getEmailMembers() {
        List<MemberEmailResDTO> memberEmailResDtoList = memberQueryService.getEmailMembers();
        return memberEmailResDtoList;
    }

    //카카오 가입 회원 조회
    @GetMapping("/members/kakao")
    @ResponseBody
    public List<MemberKakaoResDTO> getKakaoMembers() {
        List<MemberKakaoResDTO> memberKakaoResDTOList = memberQueryService.getkakaoMembers();
        return memberKakaoResDTOList;
    }

    @GetMapping("/members/me")
    @ResponseBody
    public MemberDetailResDto getMemberInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long memberId = Long.parseLong(authentication.getName()); // JWT에서 추출된 사용자 ID

        MemberDetailResDto memberInfo = memberQueryService.getMemberInfo(memberId);
        return memberInfo;
    }

    @GetMapping("/members/me/liked-topics")
    @ResponseBody
    public Page<MemberLikedTopicsResDto> getMemberLikedTopics(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long memberId = Long.parseLong(authentication.getName()); // JWT에서 추출된 사용자 ID
        Page<MemberLikedTopicsResDto> memberLikedTopics = memberQueryService.getMemberLikedTopics(memberId, pageable);
        return memberLikedTopics;
    }


    //멤버 캘린더 토픽 결과 조회
    @GetMapping("/members/topic/Results")
    @ResponseBody
    public Page<MemberTopicResultResDto> getMemberTopicResults(@RequestParam("date") LocalDate date, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long memberId = Long.parseLong(authentication.getName());
        Page<MemberTopicResultResDto> memberTopicResults = memberQueryService.getMemberTopicResultsByCreatedDate(memberId, date, pageable);
        return memberTopicResults;
    }
}
