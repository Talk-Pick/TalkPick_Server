package talkPick.domain.topic.adapter.out;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import talkPick.domain.random.adapter.out.repository.TopicStatJpaRepository;
import talkPick.domain.topic.domain.TopicStat;
import talkPick.domain.random.exception.TopicStatNotFoundException;
import talkPick.domain.topic.domain.TopicLikeHistory;
import talkPick.domain.topic.port.out.TopicCommandRepositoryPort;
import talkPick.domain.topic.adapter.out.repository.TopicLikeHistoryJpaRepository;
import talkPick.global.error.ErrorCode;

@Slf4j
@Component
@RequiredArgsConstructor
public class TopicCommandRepositoryAdapter implements TopicCommandRepositoryPort {
    private final TopicStatJpaRepository topicStatJpaRepository;
    private final TopicLikeHistoryJpaRepository topicLikeHistoryJpaRepository;

    @Override
    public void addLike(final Long memberId, final Long topicId) {
        topicLikeHistoryJpaRepository.save(TopicLikeHistory.of(memberId, topicId));
        findTopicStatByTopicId(topicId).addLike();
    }

    @Override
    public void save(Long topicId) {
        topicStatJpaRepository.save(TopicStat.of(topicId));
    }

    public TopicStat findTopicStatByTopicId(final Long topicId) {
        return topicStatJpaRepository.findByTopicId(topicId).orElseThrow(() -> new TopicStatNotFoundException(ErrorCode.TOPIC_STAT__NOT_FOUND));
    }
}