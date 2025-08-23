package talkPick.domain.search.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.search.domain.TopicSearchHistory;

/**
 * 해당 코드 사용 안 함.
 * **/
@Deprecated
public interface TopicSearchHistoryJpaRepository extends JpaRepository<TopicSearchHistory, Long> {
}
