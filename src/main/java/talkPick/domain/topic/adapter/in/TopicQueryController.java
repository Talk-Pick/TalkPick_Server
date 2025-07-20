package talkPick.domain.topic.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import talkPick.domain.topic.port.in.TopicQueryUseCase;
import talkPick.domain.topic.adapter.in.dto.TopicReqDTO;
import talkPick.domain.topic.adapter.out.dto.TopicResDTO;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TopicQueryController implements TopicQueryApi {
    private final TopicQueryUseCase topicQueryUseCase;

    @Override
    public Slice<TopicResDTO.Categories> getCategories(Pageable pageable) {
        return topicQueryUseCase.getCategories(pageable);
    }

    @Override
    public List<TopicResDTO.TopicSummaries> getTodayTopicSummaries(Long userId) {
        return topicQueryUseCase.getTodayTopicSummaries(userId);
    }

    @Override
    public List<TopicResDTO.TopicDetail> getTodayTopicDetails(List<Long> topicIds) {
        return topicQueryUseCase.getTodayTopicDetails(TopicReqDTO.TodayTopics.of(topicIds));
    }

    @Override
    public TopicResDTO.TopicDetail getTopicDetail(Long topicId) {
        return topicQueryUseCase.getTopicDetail(topicId);
    }
}
