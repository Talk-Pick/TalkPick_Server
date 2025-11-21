package talkPick.domain.topic.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import talkPick.domain.topic.port.in.TopicCommandUseCase;

@RestController
@RequiredArgsConstructor
public class TopicCommandController implements TopicCommandApi {
    private final TopicCommandUseCase topicCommandUseCase;

    @Override
    public void addLike(final Long memberId, final Long topicId) {
        topicCommandUseCase.addLike(memberId, topicId);
    }
}
