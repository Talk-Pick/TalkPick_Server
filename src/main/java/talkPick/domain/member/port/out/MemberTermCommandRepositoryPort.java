package talkPick.domain.member.port.out;

import talkPick.domain.member.domain.mapping.MemberTerm;

import java.util.Optional;

public interface MemberTermCommandRepositoryPort {
    Optional<MemberTerm> findByMemberIdAndTermId(Long memberId, Long termId);
    MemberTerm save(MemberTerm memberTerm);
    void deleteByMemberId(Long memberId);
}


