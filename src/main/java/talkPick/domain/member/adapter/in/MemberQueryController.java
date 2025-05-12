package talkPick.domain.member.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import talkPick.domain.member.adapter.in.dto.KakaoUserInfo;
import talkPick.domain.member.adapter.out.dto.MemberEmailResDTO;
import talkPick.domain.member.adapter.out.dto.MemberKakaoResDTO;
import talkPick.domain.member.application.MemberQueryService;
import talkPick.domain.member.port.in.MemberQueryUseCase;

import java.io.IOException;
import java.util.List;

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
    public List<MemberKakaoResDTO> getKakaoMembers() {
        List<MemberKakaoResDTO> memberKakaoResDTOList = memberQueryService.getkakaoMembers();
        return memberKakaoResDTOList;
    }

    //mbti 입력 페이지
    @GetMapping("/topic/additional")
    public String showMbtiForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("mbti 입력 페이지");
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userInfo") == null) {
            log.error("세션이 만료되었거나 카카오 사용자 정보가 없습니다.");
            return "redirect:/oauth/kakao/authorize"; // 스프링 MVC의 리다이렉트 방식
        }

        KakaoUserInfo userInfo = (KakaoUserInfo) session.getAttribute("userInfo");

        // 뷰 이름 반환
        return "mbti-form";
    }



}
