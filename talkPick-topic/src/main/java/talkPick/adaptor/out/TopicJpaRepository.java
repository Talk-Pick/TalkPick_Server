package talkPick.adaptor.out;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.Topic;

public interface TopicJpaRepository extends JpaRepository<Topic, Long> {
}
