package talkPick.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import talkPick.port.in.MemberQueryUseCase;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class TopicQueryController implements TopicQueryApi {
    private final MemberQueryUseCase memberQueryUseCase;
}
