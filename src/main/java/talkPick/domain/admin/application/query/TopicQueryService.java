package talkPick.domain.admin.application.query;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.topic.adapter.in.dto.TopicReqDTO;
import talkPick.domain.topic.adapter.out.dto.TopicResDTO;
import talkPick.global.common.model.PageCustom;
import talkPick.domain.topic.port.in.TopicQueryUseCase;
import talkPick.domain.topic.port.out.TopicQueryRepositoryPort;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TopicQueryService implements TopicQueryUseCase {
    private final TopicQueryRepositoryPort topicQueryRepositoryPort;

    @Override
    public PageCustom<TopicResDTO.Categories> getCategories(Pageable pageable) {
        return topicQueryRepositoryPort.findCategoriesWithPageable(pageable);
    }

    @Override
    public List<TopicResDTO.TopicSummaries> getTodayTopicSummaries(Long userId) {
        return topicQueryRepositoryPort.findTodayTopicSummaries(userId);
    }

    @Override
    public List<TopicResDTO.TopicDetail> getTodayTopicDetails(TopicReqDTO.TodayTopics requestDTO) {
        return topicQueryRepositoryPort.findTodayTopicDetails(requestDTO);
    }

    @Override
    public TopicResDTO.TopicDetail getTopicDetail(Long topicId) {
        return topicQueryRepositoryPort.findTopicDetail(topicId);
    }
}