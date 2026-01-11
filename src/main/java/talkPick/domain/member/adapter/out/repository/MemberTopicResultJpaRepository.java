package talkPick.domain.member.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import talkPick.domain.topic.domain.member.MemberTopicResult;

import java.util.Optional;

public interface MemberTopicResultJpaRepository extends JpaRepository<MemberTopicResult, Long> {
    Optional<MemberTopicResult> findByMemberTopicHistoryId(Long memberTopicHistoryId);

    void deleteByMemberId(Long memberId);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM MemberTopicResult m WHERE m.memberId = :memberId")
    void deleteAllByMemberIdInBulk(@Param("memberId") Long memberId);
}
