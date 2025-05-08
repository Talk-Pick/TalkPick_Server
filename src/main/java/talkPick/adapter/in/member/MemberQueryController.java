package talkPick.adapter.in.member;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import talkPick.port.in.member.MemberQueryUseCase;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberQueryController implements MemberQueryApi {
    private final MemberQueryUseCase memberQueryUseCase;
}
