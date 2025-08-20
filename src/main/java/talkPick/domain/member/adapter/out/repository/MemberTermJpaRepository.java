package talkPick.domain.member.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.member.domain.mapping.MemberTerm;
import talkPick.domain.term.domain.Term;

import java.util.Optional;

public interface MemberTermJpaRepository extends JpaRepository<MemberTerm, Long> {
    // 특정 약관 및 유저의 동의 상태 조회
    Optional<MemberTerm> findByMemberIdAndTerm(Long memberId, Term term);
}
