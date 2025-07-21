package talkPick.domain.today.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.today.domain.TodayTopic;

public interface TodayTopicJpaRepository extends JpaRepository<TodayTopic, Long> {
}
