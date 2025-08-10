package talkPick.domain.token.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.member.domain.Member;
import talkPick.domain.token.domain.MemberToken;

import java.util.Optional;

public interface MemberTokenJpaRepository extends JpaRepository<MemberToken,Long> {
    Optional<MemberToken> findByRefreshToken(String refreshToken);
    Optional<MemberToken> findByMember(Member member);
    void deleteByMember(Member member);
}
