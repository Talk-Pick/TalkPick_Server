package talkPick.domain.topic.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.topic.domain.Keyword;

public interface KeywordJpaRepository extends JpaRepository<Keyword, Long> {
}
