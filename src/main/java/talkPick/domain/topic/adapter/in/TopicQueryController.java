package talkPick.domain.topic.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import talkPick.domain.topic.port.in.TopicQueryUseCase;
import talkPick.domain.topic.adapter.in.dto.TopicReqDTO;
import talkPick.domain.topic.adapter.out.dto.TopicResDTO;
import talkPick.global.annotation.UserId;
import talkPick.global.common.model.PageCustom;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/topic")
public class TopicQueryController implements TopicQueryApi {
    private final TopicQueryUseCase topicQueryUseCase;

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
    public List<TopicResDTO.TopicDetail> getTodayTopicDetails(TopicReqDTO.TodayTopics requestDTO) {
        return topicQueryUseCase.getTodayTopicDetails(requestDTO);
    }

    @Override
    @GetMapping("/{topicId}")
    public TopicResDTO.TopicDetail getTopicDetail(@PathVariable("topicId") Long topicId) {
        return topicQueryUseCase.getTopicDetail(topicId);
    }
}
