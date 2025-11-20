package talkPick.domain.topic.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import talkPick.domain.topic.domain.TopicStat;

import java.util.Optional;

public interface TopicStatJpaRepository extends JpaRepository<TopicStat, Long> {
    Optional<TopicStat> findByTopicId(Long topicId);

    @Modifying
    @Query("UPDATE TopicStat SET likeCount = likeCount + 1 WHERE topicId = :topicId")
    void incrementLikeCount(@Param("topicId") Long topicId);
}
