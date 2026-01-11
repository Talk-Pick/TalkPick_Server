package talkPick.domain.topic.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import talkPick.domain.topic.port.in.TopicQueryUseCase;
import talkPick.domain.topic.adapter.out.dto.TopicResDTO;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TopicQueryController implements TopicQueryApi {
    private final TopicQueryUseCase topicQueryUseCase;

    @Override
    public List<TopicResDTO.Categories> getCategories() {
        return topicQueryUseCase.getCategories();
    }

    @Override
    public TopicResDTO.TopicDetail getTopicDetail(Long topicId) {
        return topicQueryUseCase.getTopicDetail(topicId);
    }
}
