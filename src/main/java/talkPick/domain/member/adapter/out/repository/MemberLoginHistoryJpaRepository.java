package talkPick.domain.member.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.member.domain.MemberLoginHistory;

public interface MemberLoginHistoryJpaRepository extends JpaRepository<MemberLoginHistory, Long> {
    void deleteByMemberId(Long memberId);
}
