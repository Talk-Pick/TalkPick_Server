package talkPick.domain.search.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.search.domain.TopicSearchHistory;

public interface TopicSearchHistoryJpaRepository extends JpaRepository<TopicSearchHistory, Long> {
}
