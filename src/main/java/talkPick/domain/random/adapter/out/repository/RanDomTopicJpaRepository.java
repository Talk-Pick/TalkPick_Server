package talkPick.domain.random.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.random.domain.RandomTopic;

public interface RanDomTopicJpaRepository extends JpaRepository<RandomTopic, Long> {
}
