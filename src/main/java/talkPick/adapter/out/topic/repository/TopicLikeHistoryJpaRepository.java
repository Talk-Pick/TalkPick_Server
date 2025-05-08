package talkPick.adapter.out.topic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.topic.like.TopicLikeHistory;

public interface TopicLikeHistoryJpaRepository extends JpaRepository<TopicLikeHistory, Long> {
}
