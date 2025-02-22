package talkPick.adaptor.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.adaptor.out.dto.TopicResDTO;
import talkPick.port.out.TopicQueryRepositoryPort;

@Component
@RequiredArgsConstructor
public class TopicQueryRepositoryAdaptor implements TopicQueryRepositoryPort {
    private final TopicJpaRepository topicJpaRepository;
    @Override
    public TopicResDTO.Categories findCategories() {
        return null;
    }
}
