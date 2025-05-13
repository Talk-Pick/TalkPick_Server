package talkPick.domain.random.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.topic.domain.TopicStat;

import java.util.Optional;

public interface TopicStatJpaRepository extends JpaRepository<TopicStat, Long> {
    Optional<TopicStat> findByTopicId(Long topicId);
}
