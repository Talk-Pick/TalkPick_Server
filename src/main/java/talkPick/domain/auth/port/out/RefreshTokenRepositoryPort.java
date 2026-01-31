package talkPick.domain.auth.port.out;

import talkPick.domain.auth.domain.RefreshTokenInfo;
import talkPick.domain.member.domain.Member;

import java.util.Optional;

public interface RefreshTokenRepositoryPort {
    RefreshTokenInfo save(RefreshTokenInfo refreshTokenInfo, Member member);
    Optional<RefreshTokenInfo> findByToken(String token);
    Optional<RefreshTokenInfo> findByMemberId(Long memberId);
    void deleteByToken(String token);
    void deleteByMemberId(Long memberId);
    Member findMemberByToken(String token);
}