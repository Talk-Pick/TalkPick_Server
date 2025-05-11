package talkPick.domain.topic.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.topic.domain.Topic;

public interface TopicJpaRepository extends JpaRepository<Topic, Long> {
}
