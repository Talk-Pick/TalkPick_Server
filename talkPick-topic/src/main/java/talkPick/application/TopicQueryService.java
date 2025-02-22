package talkPick.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.adaptor.out.dto.TopicResDTO;
import talkPick.port.in.TopicQueryUseCase;
import talkPick.port.out.TopicQueryRepositoryPort;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TopicQueryService implements TopicQueryUseCase {
    private final TopicQueryRepositoryPort topicQueryRepositoryPort;

    @Override
    public TopicResDTO.Categories getCategories() {
        return topicQueryRepositoryPort.findCategories();
    }
}
