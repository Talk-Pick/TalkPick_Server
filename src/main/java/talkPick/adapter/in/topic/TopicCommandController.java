package talkPick.adapter.in.topic;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import talkPick.common.annotation.UserId;
import talkPick.port.in.topic.TopicCommandUseCase;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/topic")
public class TopicCommandController implements TopicCommandApi {
    private final TopicCommandUseCase topicCommandUseCase;

    @PostMapping("/{topicId}/like")
    public void addLike(@UserId final Long memberId, @PathVariable("topicId") final Long topicId) {
        topicCommandUseCase.addLike(memberId, topicId);
    }
}
