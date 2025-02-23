package talkPick.adaptor.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.adaptor.out.repository.TopicJpaRepository;
import talkPick.adaptor.out.repository.TopicQuerydslRepository;
import talkPick.port.out.TopicCommandRepositoryPort;

@Component
@RequiredArgsConstructor
public class TopicCommandRepositoryAdaptor implements TopicCommandRepositoryPort {
    private final TopicQuerydslRepository topicQuerydslRepository;
    private final TopicJpaRepository topicJpaRepository;
}
