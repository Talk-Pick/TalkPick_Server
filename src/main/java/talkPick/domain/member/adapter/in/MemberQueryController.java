package talkPick.domain.member.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import talkPick.domain.member.adapter.out.dto.MemberEmailResDTO;
import talkPick.domain.member.adapter.out.dto.MemberKakaoResDTO;
import talkPick.domain.member.application.MemberQueryService;
import talkPick.domain.member.port.in.MemberQueryUseCase;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberQueryController implements MemberQueryApi {
    private final MemberQueryUseCase memberQueryUseCase;
    private final MemberQueryService memberQueryService;

    //이메일 가입 회원 조회
    @GetMapping("join/email")
    public List<MemberEmailResDTO> getEmailMembers() {
        List<MemberEmailResDTO> memberEmailResDtoList = memberQueryService.getEmailMembers();
        return memberEmailResDtoList;
    }

    //카카오 가입 회원 조회
    @GetMapping("join/kakao")
    public List<MemberKakaoResDTO> getKakaoMembers() {
        List<MemberKakaoResDTO> memberKakaoResDTOList = memberQueryService.getkakaoMembers();
        return memberKakaoResDTOList;
    }
}
