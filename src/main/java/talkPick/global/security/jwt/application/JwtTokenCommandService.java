package talkPick.global.security.jwt.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.admin.domain.type.Role;
import talkPick.domain.member.adapter.in.dto.MemberReqDto;
import talkPick.domain.member.domain.Member;
import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.handler.JwtExceptionHandler;
import talkPick.global.security.jwt.RefreshToken;
import talkPick.global.security.jwt.dto.JwtResDTO;
import talkPick.global.security.jwt.port.in.JwtTokenCommandUseCase;
import talkPick.global.security.jwt.repository.RefreshTokenRepository;
import talkPick.global.security.jwt.util.JwtGenerator;
import talkPick.global.security.jwt.util.JwtProvider;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtTokenCommandService implements JwtTokenCommandUseCase {
    private final JwtProvider jwtProvider;
    private final JwtGenerator jwtGenerator;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    // 회원 정보를 기반으로 JWT 로그인 토큰과 리프레시 토큰을 생성하고 리프레시 토큰을 DB에 저장하는 메서드
    public JwtResDTO.Login generateToken(Member member) {
        // 회원 ID와 Role 정보를 기반으로 JWT 토큰 생성
        JwtResDTO.Login jwt = jwtProvider.createJwt(member.getId(), String.valueOf(Role.MEMBER));

        // JWT에서 리프레시 토큰 만료 시간을 LocalDateTime으로 변환 (UTC 기준)
        LocalDateTime refreshExpireAt = LocalDateTime.ofEpochSecond(jwt.refreshExpiredTime(), 0, ZoneOffset.UTC);

        // 리프레시 토큰 정보(토큰, 회원 ID, 역할, 만료 시간)를 DB에 저장
        refreshTokenRepository.save(RefreshToken.of(jwt.refreshToken(), member.getId(), String.valueOf(Role.MEMBER), refreshExpireAt));

        // 생성한 JWT 로그인 토큰 반환
        return jwt;
    }

    @Override
    @Transactional
    // 클라이언트에서 전달받은 리프레시 토큰으로 액세스 토큰을 재발급하는 메서드
    public JwtResDTO.AccessToken refreshAccessToken(MemberReqDto.RefreshAccessTokenRequest request) {
        // DB에서 리프레시 토큰 조회
        RefreshToken refresh = refreshTokenRepository.findByToken(request.getRefreshToken());

        // 리프레시 토큰이 없으면 예외 발생 (유효하지 않은 토큰)
        if (refresh == null) {
            throw new JwtExceptionHandler(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        // 리프레시 토큰이 만료됐으면 DB에서 삭제하고 예외 발생
        if (refresh.getExpiredAt().isBefore(LocalDateTime.now(ZoneOffset.UTC))) {
            refreshTokenRepository.deleteByMemberId(refresh.getMemberId());
            throw new JwtExceptionHandler(ErrorCode.EXPIRED_REFRESH_TOKEN);
        }

        // 유효한 리프레시 토큰이면 해당 회원 ID와 역할을 기반으로 새 액세스 토큰 생성
        JwtResDTO.AccessToken accessToken = jwtGenerator.generateAccessToken(refresh.getMemberId(), refresh.getRole());

        // 새로 생성한 액세스 토큰 반환
        return accessToken;
    }

}


