package talkPick.domain.topic.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.topic.domain.TopicLikeHistory;

public interface TopicLikeHistoryJpaRepository extends JpaRepository<TopicLikeHistory, Long> {
}
