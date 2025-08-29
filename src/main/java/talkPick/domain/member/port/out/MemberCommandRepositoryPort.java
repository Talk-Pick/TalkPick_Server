package talkPick.domain.member.port.out;

import talkPick.domain.member.domain.Member;

import java.util.Optional;

public interface MemberCommandRepositoryPort {
    Member save(Member member);
    Optional<Member> findById(Long memberId);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByProviderId(String providerId);
}


