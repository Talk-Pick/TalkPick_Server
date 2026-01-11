package talkPick.domain.member.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import talkPick.domain.member.domain.mapping.MemberTerm;

import java.util.Optional;

public interface MemberTermJpaRepository extends JpaRepository<MemberTerm, Long> {
    // 특정 약관 및 유저의 동의 상태 조회
    Optional<MemberTerm> findByMemberIdAndTermId(Long memberId, Long termId);

    void deleteByMemberId(Long memberId);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM MemberTerm m WHERE m.memberId = :memberId")
    void deleteAllByMemberIdInBulk(@Param("memberId") Long memberId);
}