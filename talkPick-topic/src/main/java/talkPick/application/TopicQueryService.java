package talkPick.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.adapter.in.dto.TopicReqDTO;
import talkPick.adapter.out.dto.TopicResDTO;
import talkPick.domain.type.Category;
import talkPick.model.PageCustom;
import talkPick.port.in.TopicQueryUseCase;
import talkPick.port.out.TopicQueryRepositoryPort;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TopicQueryService implements TopicQueryUseCase {
    private final TopicQueryRepositoryPort topicQueryRepositoryPort;

    @Override
    public List<TopicResDTO.Categories> getTopCategories() {
        return Stream.of(Category.values())
                .map(category -> new TopicResDTO.Categories(category.name(), category.getDescription(), category.getImageUrl()))
                .collect(Collectors.toList());
    }

    @Override
    public PageCustom<TopicResDTO.Categories> getCategories(Pageable pageable) {
        return topicQueryRepositoryPort.findCategoriesWithPageable(pageable);
    }

    @Override
    public List<TopicResDTO.TopicSummaries> getTodayTopicSummaries(Long userId) {
        return topicQueryRepositoryPort.findTodayTopicSummaries(userId);
    }

    @Override
    public List<TopicResDTO.TopicDetails> getTodayTopicDetails(TopicReqDTO.TodayTopics requestDTO) {
        return topicQueryRepositoryPort.findTodayTopicDetails(requestDTO);
    }
}