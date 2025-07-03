package talkPick.domain.topic.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import talkPick.global.security.annotation.UserId;
import talkPick.domain.topic.port.in.TopicCommandUseCase;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/topic")
public class TopicCommandController implements TopicCommandApi {
    private final TopicCommandUseCase topicCommandUseCase;

    @Override
    @PostMapping("/{topicId}/like")
    public void addLike(@UserId final Long memberId, @PathVariable("topicId") final Long topicId) {
        topicCommandUseCase.addLike(memberId, topicId);
    }
}
