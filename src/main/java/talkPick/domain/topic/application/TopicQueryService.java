package talkPick.domain.topic.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.topic.adapter.out.dto.TopicResDTO;
import talkPick.domain.topic.port.in.TopicQueryUseCase;
import talkPick.domain.topic.port.out.TopicQueryRepositoryPort;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TopicQueryService implements TopicQueryUseCase {
    private final TopicQueryRepositoryPort topicQueryRepositoryPort;

    @Override
    public List<TopicResDTO.Categories> getCategories() {
        return topicQueryRepositoryPort.findCategories();
    }

    @Override
    public TopicResDTO.TopicDetail getTopicDetail(Long topicId) {
        return topicQueryRepositoryPort.findTopicDetail(topicId);
    }
}