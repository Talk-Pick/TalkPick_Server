package talkPick.domain.topic.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.RestController;
import talkPick.domain.topic.port.in.TopicQueryUseCase;
import talkPick.domain.topic.adapter.out.dto.TopicResDTO;

@RestController
@RequiredArgsConstructor
public class TopicQueryController implements TopicQueryApi {
    private final TopicQueryUseCase topicQueryUseCase;

    @Override
    public Slice<TopicResDTO.Categories> getCategories(Pageable pageable) {
        return topicQueryUseCase.getCategories(pageable);
    }

    @Override
    public TopicResDTO.TopicDetail getTopicDetail(Long topicId) {
        return topicQueryUseCase.getTopicDetail(topicId);
    }
}
