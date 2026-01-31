package talkPick.domain.auth.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.core.common.exception.ErrorCode;
import talkPick.core.common.exception.handler.JwtExceptionHandler;
import talkPick.domain.auth.domain.RefreshTokenInfo;
import talkPick.domain.auth.port.in.GenerateTokenUseCase;
import talkPick.domain.auth.port.in.RefreshTokenUseCase;
import talkPick.domain.auth.port.out.RefreshTokenRepositoryPort;
import talkPick.domain.auth.port.out.TokenGeneratorPort;
import talkPick.domain.auth.adapter.out.dto.TokenResponse;
import talkPick.domain.member.domain.Member;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService implements GenerateTokenUseCase, RefreshTokenUseCase {
    private final TokenGeneratorPort tokenGeneratorPort;
    private final RefreshTokenRepositoryPort refreshTokenRepositoryPort;

    private static final int REFRESH_TOKEN_EXPIRE_WEEKS = 4;

    @Override
    @Transactional
    public TokenResponse.GeneratedTokens generateToken(Member member) {
        String refreshToken = tokenGeneratorPort.generateRefreshToken();
        LocalDateTime expireAt = LocalDateTime.now().plusWeeks(REFRESH_TOKEN_EXPIRE_WEEKS);

        RefreshTokenInfo newTokenInfo = RefreshTokenInfo.of(member.getId(), refreshToken, expireAt);

        Optional<RefreshTokenInfo> existingTokenOpt = refreshTokenRepositoryPort.findByMemberId(member.getId());

        RefreshTokenInfo tokenInfoToSave;
        if (existingTokenOpt.isPresent()) {
            tokenInfoToSave = existingTokenOpt.get().updateToken(refreshToken, expireAt);
        } else {
            tokenInfoToSave = newTokenInfo;
        }

        RefreshTokenInfo savedTokenInfo = refreshTokenRepositoryPort.save(tokenInfoToSave, member);

        TokenResponse.AccessToken accessToken = tokenGeneratorPort.generateAccessToken(
                member.getId(),
                member.getMemberRole().toString()
        );

        return new TokenResponse.GeneratedTokens(
                accessToken.accessToken(),
                savedTokenInfo.token(),
                accessToken.accessExpiredTime(),
                savedTokenInfo.expiredAt().atZone(ZoneId.systemDefault()).toEpochSecond()
        );
    }

    @Override
    @Transactional
    public TokenResponse.AccessToken refreshAccessToken(String refreshToken) {
        RefreshTokenInfo tokenInfo = refreshTokenRepositoryPort.findByToken(refreshToken)
                .orElseThrow(() -> new JwtExceptionHandler(ErrorCode.INVALID_REFRESH_TOKEN));

        if (tokenInfo.isExpired()) {
            refreshTokenRepositoryPort.deleteByToken(refreshToken);
            throw new JwtExceptionHandler(ErrorCode.EXPIRED_REFRESH_TOKEN);
        }

        Member member = refreshTokenRepositoryPort.findMemberByToken(refreshToken);

        return tokenGeneratorPort.generateAccessToken(
                member.getId(),
                member.getMemberRole().toString()
        );
    }
}
