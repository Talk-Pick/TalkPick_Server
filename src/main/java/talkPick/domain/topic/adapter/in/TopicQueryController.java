package talkPick.domain.topic.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import talkPick.domain.topic.port.in.TopicQueryUseCase;
import talkPick.domain.topic.adapter.in.dto.TopicReqDTO;
import talkPick.domain.topic.adapter.out.dto.TopicResDTO;
import talkPick.global.security.annotation.UserId;
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
    public List<TopicResDTO.TopicSummaries> getTodayTopicSummaries(@UserId Long userId) {
        return topicQueryUseCase.getTodayTopicSummaries(userId);
    }

    @Override
    public List<TopicResDTO.TopicDetail> getTodayTopicDetails(TopicReqDTO.TodayTopics requestDTO) {
        return topicQueryUseCase.getTodayTopicDetails(requestDTO);
    }

    @Override
    public TopicResDTO.TopicDetail getTopicDetail(@PathVariable("topicId") Long topicId) {
        return topicQueryUseCase.getTopicDetail(topicId);
    }
}
