package talkPick.adapter.out.topic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.topic.Topic;

public interface TopicJpaRepository extends JpaRepository<Topic, Long> {
}
