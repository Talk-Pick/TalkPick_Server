package talkPick.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.adapter.in.dto.TopicReqDTO;
import talkPick.adapter.out.dto.TopicResDTO;
import talkPick.model.PageCustom;
import talkPick.port.in.TopicQueryUseCase;
import talkPick.port.out.TopicQueryRepositoryPort;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TopicQueryService implements TopicQueryUseCase {
    private final TopicQueryRepositoryPort topicQueryRepositoryPort;

    @Override
    public List<TopicResDTO.Categories> getTopCategories() {
        return topicQueryRepositoryPort.findTopCategories();
    }

    @Override
    public PageCustom<TopicResDTO.Categories> getCategories(Pageable pageable) {
        return topicQueryRepositoryPort.findCategoriesWithPageable(pageable);
    }

    @Override
    public List<TopicResDTO.TopicSummaries> getTodayTopicSummaries() {
        return topicQueryRepositoryPort.findTodayTopicSummaries();
    }

    @Override
    public List<TopicResDTO.TopicDetails> getTodayTopicDetails(TopicReqDTO.TodayTopics requestDTO) {
        return topicQueryRepositoryPort.findTodayTopicDetails(requestDTO);
    }
}