package talkPick.domain.member.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import talkPick.domain.member.adapter.in.dto.MemberEmailReqDTO;
import talkPick.domain.member.application.MemberQueryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/topic")
public class MemberCommandController {

    private final MemberQueryService memberQueryService;

    //이메일 기반 회원가입
    @PostMapping("/members/join")
    public void joinEmailMember(@RequestBody MemberEmailReqDTO memberReqDto, HttpServletRequest request) {
        memberQueryService.setEmailMember(memberReqDto);
        HttpSession session = request.getSession();
        session.setAttribute("userInfo", memberReqDto);
    }

}
