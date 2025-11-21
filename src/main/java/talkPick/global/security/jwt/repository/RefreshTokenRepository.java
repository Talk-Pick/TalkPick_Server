package talkPick.global.security.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import talkPick.domain.member.domain.Member;
import talkPick.global.security.jwt.RefreshToken;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByMember(Member member);
    Optional<RefreshToken> findByMemberId(Long memberId);
    void deleteByMember(Member member);
}