package talkPick.domain.topic.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import talkPick.domain.topic.domain.TopicLikeHistory;
import talkPick.global.model.TalkPickStatus;

import java.util.Optional;

public interface TopicLikeHistoryJpaRepository extends JpaRepository<TopicLikeHistory, Long> {
    void deleteByMemberId(Long memberId);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM TopicLikeHistory t WHERE t.memberId = :memberId")
    void deleteAllByMemberIdInBulk(@Param("memberId") Long memberId);

    Optional<TopicLikeHistory> findFirstByMemberIdAndTopicIdAndStatusOrderByIdDesc(
            Long memberId, Long topicId, TalkPickStatus status
    );
}