package talkPick.domain.topic.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.topic.domain.TopicKeyword;

public interface TopicKeywordRepository extends JpaRepository<TopicKeyword, Long> {
}
