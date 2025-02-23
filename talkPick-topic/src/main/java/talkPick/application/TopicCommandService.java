package talkPick.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.port.in.TopicCommandUseCase;
import talkPick.port.out.TopicCommandRepositoryPort;
import talkPick.port.out.TopicQueryRepositoryPort;

@Service
@Transactional
@RequiredArgsConstructor
public class TopicCommandService implements TopicCommandUseCase {
    private final TopicCommandRepositoryPort topicCommandRepository;
    private final TopicQueryRepositoryPort topicQueryRepositoryPort;

    @Override
    public void addLike(Long memberId, Long topicId) {
        //TODO memberId 유효한 멤버인지 확인해야 함.

        var findTopic = topicQueryRepositoryPort.findTopicById(topicId);

        //TODO 동시성 제어해야 함.
        findTopic.addLike();
    }
}
