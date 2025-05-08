package talkPick.adapter.in.topic;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import talkPick.adapter.in.topic.dto.TopicReqDTO;
import talkPick.adapter.out.topic.dto.TopicResDTO;
import talkPick.common.annotation.UserId;
import talkPick.common.model.PageCustom;
import talkPick.port.in.topic.TopicQueryUseCase;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/topic")
public class TopicQueryController implements TopicQueryApi {
    private final TopicQueryUseCase topicQueryUseCase;

    @Override
    @GetMapping("/top-categories")
    public List<TopicResDTO.Categories> getTopCategories() {
        return topicQueryUseCase.getTopCategories();
    }

    @Override
    @GetMapping("/total-categories")
    public PageCustom<TopicResDTO.Categories> getCategories(Pageable pageable) {
        return topicQueryUseCase.getCategories(pageable);
    }

    @Override
    @GetMapping("/today-topics")
    public List<TopicResDTO.TopicSummaries> getTodayTopicSummaries(@UserId Long userId) {
        return topicQueryUseCase.getTodayTopicSummaries(userId);
    }

    @Override
    @GetMapping("/today-topic-details")
    public List<TopicResDTO.TopicDetails> getTodayTopicDetails(TopicReqDTO.TodayTopics requestDTO) {
        return topicQueryUseCase.getTodayTopicDetails(requestDTO);
    }
}
