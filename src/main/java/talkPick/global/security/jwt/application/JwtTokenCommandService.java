package talkPick.global.security.jwt.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.member.adapter.in.dto.MemberReqDto;
import talkPick.domain.member.domain.Member;
import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.handler.JwtExceptionHandler;
import talkPick.global.security.jwt.RefreshToken;
import talkPick.global.security.jwt.dto.JwtResDTO;
import talkPick.global.security.jwt.port.in.JwtTokenCommandUseCase;
import talkPick.global.security.jwt.repository.RefreshTokenRepository;
import talkPick.global.security.jwt.util.JwtGenerator;
import talkPick.global.security.jwt.util.RefreshTokenGenerator;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtTokenCommandService implements JwtTokenCommandUseCase {
    private final JwtGenerator jwtGenerator;
    private final RefreshTokenGenerator refreshTokenGenerator;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public JwtResDTO.GeneratedTokens generateToken(Member member) {
        // 1. 새로운 (저장되지 않은) RefreshToken 객체를 생성합니다.
        RefreshToken newRefreshToken = refreshTokenGenerator.generateRefreshToken(member);

        // 2. 이 회원의 기존 토큰을 확인합니다.
        Optional<RefreshToken> existingTokenOpt = refreshTokenRepository.findByMember(member);

        RefreshToken refreshTokenToSave;
        if (existingTokenOpt.isPresent()) {
            // 3a. 기존 토큰이 있으면 새 토큰 값과 만료 시간으로 업데이트합니다.
            refreshTokenToSave = existingTokenOpt.get();
            refreshTokenToSave.updateToken(newRefreshToken.getToken(), newRefreshToken.getExpiredAt());
        } else {
            // 3b. 없으면 새로 생성된 토큰을 사용합니다.
            refreshTokenToSave = newRefreshToken;
        }

        // 4. 토큰을 저장합니다 (업데이트 또는 신규).
        refreshTokenRepository.save(refreshTokenToSave);

        // 5. 액세스 토큰을 생성합니다.
        JwtResDTO.AccessToken accessToken = jwtGenerator.generateAccessToken(member.getId(), member.getMemberRole().toString());

        // 6. 응답 DTO를 생성하여 반환합니다.
        return new JwtResDTO.GeneratedTokens(
                accessToken.accessToken(),
                refreshTokenToSave.getToken(),
                accessToken.accessExpiredTime(),
                refreshTokenToSave.getExpiredAt().atZone(java.time.ZoneId.systemDefault()).toEpochSecond()
        );
    }

    @Override
    @Transactional
    public JwtResDTO.AccessToken refreshAccessToken(String refreshToken) {
        // DB에서 리프레시 토큰 조회
        RefreshToken refresh = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new JwtExceptionHandler(ErrorCode.INVALID_REFRESH_TOKEN));

        // 리프레시 토큰이 만료됐으면 DB에서 삭제하고 예외 발생
        if (refresh.getExpiredAt().isBefore(LocalDateTime.now(ZoneOffset.UTC))) {
            refreshTokenRepository.delete(refresh);
            throw new JwtExceptionHandler(ErrorCode.EXPIRED_REFRESH_TOKEN);
        }

        // 유효한 리프레시 토큰이면 해당 회원 ID와 역할을 기반으로 새 액세스 토큰 생성
        Member member = refresh.getMember();
        JwtResDTO.AccessToken accessToken = jwtGenerator.generateAccessToken(member.getId(), member.getMemberRole().toString());

        // 새로 생성한 액세스 토큰 반환
        return accessToken;
    }
}

