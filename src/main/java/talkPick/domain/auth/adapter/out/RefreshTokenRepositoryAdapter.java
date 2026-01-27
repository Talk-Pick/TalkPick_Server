package talkPick.domain.auth.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.core.common.exception.ErrorCode;
import talkPick.core.common.exception.handler.JwtExceptionHandler;
import talkPick.domain.auth.domain.RefreshToken;
import talkPick.domain.auth.domain.RefreshTokenInfo;
import talkPick.domain.auth.port.out.RefreshTokenRepositoryPort;
import talkPick.domain.member.domain.Member;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RefreshTokenRepositoryAdapter implements RefreshTokenRepositoryPort {
    private final RefreshTokenJpaRepository refreshTokenJpaRepository;

    @Override
    public RefreshTokenInfo save(RefreshTokenInfo refreshTokenInfo, Member member) {
        Optional<RefreshToken> existingEntity = refreshTokenJpaRepository.findByMemberId(member.getId());

        RefreshToken entityToSave;
        if (existingEntity.isPresent()) {
            entityToSave = existingEntity.get();
            entityToSave.updateToken(refreshTokenInfo.token(), refreshTokenInfo.expiredAt());
        } else {
            entityToSave = RefreshToken.fromDomain(refreshTokenInfo, member);
        }

        RefreshToken savedEntity = refreshTokenJpaRepository.save(entityToSave);
        return savedEntity.toDomain();
    }

    @Override
    public Optional<RefreshTokenInfo> findByToken(String token) {
        return refreshTokenJpaRepository.findByToken(token)
                .map(RefreshToken::toDomain);
    }

    @Override
    public Optional<RefreshTokenInfo> findByMemberId(Long memberId) {
        return refreshTokenJpaRepository.findByMemberId(memberId)
                .map(RefreshToken::toDomain);
    }

    @Override
    public void deleteByToken(String token) {
        refreshTokenJpaRepository.deleteByToken(token);
    }

    @Override
    public void deleteByMemberId(Long memberId) {
        refreshTokenJpaRepository.deleteAllByMemberIdInBulk(memberId);
    }

    @Override
    public Member findMemberByToken(String token) {
        return refreshTokenJpaRepository.findByToken(token)
                .map(RefreshToken::getMember)
                .orElseThrow(() -> new JwtExceptionHandler(ErrorCode.INVALID_REFRESH_TOKEN));
    }
}