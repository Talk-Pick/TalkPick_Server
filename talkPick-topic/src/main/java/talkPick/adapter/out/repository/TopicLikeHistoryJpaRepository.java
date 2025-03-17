package talkPick.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.like.TopicLikeHistory;

public interface TopicLikeHistoryJpaRepository extends JpaRepository<TopicLikeHistory, Long> {
}
