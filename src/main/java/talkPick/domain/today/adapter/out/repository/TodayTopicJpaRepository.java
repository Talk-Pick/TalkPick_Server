package talkPick.domain.today.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import talkPick.domain.today.domain.TodayTopic;

public interface TodayTopicJpaRepository extends JpaRepository<TodayTopic, Long> {
    void deleteByMemberId(Long memberId);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM TodayTopic t WHERE t.memberId = :memberId")
    void deleteAllByMemberIdInBulk(@Param("memberId") Long memberId);
}