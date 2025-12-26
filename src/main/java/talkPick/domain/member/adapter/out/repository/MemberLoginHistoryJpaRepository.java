package talkPick.domain.member.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import talkPick.domain.member.domain.MemberLoginHistory;

public interface MemberLoginHistoryJpaRepository extends JpaRepository<MemberLoginHistory, Long> {
    void deleteByMemberId(Long memberId);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM MemberLoginHistory m WHERE m.memberId = :memberId")
    void deleteAllByMemberIdInBulk(@Param("memberId") Long memberId);
}