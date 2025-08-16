package talkPick.domain.token.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.admin.domain.type.Role;
import talkPick.domain.kakao.converter.KakaoConverter;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.dto.MemberReqDto;
import talkPick.domain.member.dto.MemberResDto;
import talkPick.domain.token.adapter.out.repository.MemberTokenJpaRepository;
import talkPick.domain.token.converter.MemberTokenConverter;
import talkPick.domain.token.domain.MemberToken;
import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.handler.JwtHandler;
import talkPick.global.security.jwt.dto.JwtResDTO;
import talkPick.global.security.jwt.util.JwtGenerator;
import talkPick.global.security.jwt.util.JwtProvider;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberTokenCommandService implements MemberTokenCommandUseCase {
    private final JwtProvider jwtProvider;
    private final MemberTokenJpaRepository memberTokenRepository;
    private final JwtGenerator jwtGenerator;

    /**
     * 유저 데이터를 통해 Access/Refresh 토큰 생성하는 메서드
     */
    @Override
    public MemberResDto.LoginTokenResponse generateToken(Member member) {
        MemberToken findMemberToken = memberTokenRepository.findByMember(member)
                .orElse(null);

        // Access Token & Refresh Token 생성
        JwtResDTO.Login jwt = jwtProvider.createJwt(member.getId(), String.valueOf(Role.ADMIN));
        LocalDateTime refreshExpireAt =
                LocalDateTime.ofEpochSecond(jwt.refreshExpiredTime(), 0, ZoneOffset.UTC);

        // 이미 MemberToken 이 있다면 UPDATE, 없다면 INSERT
        if (findMemberToken == null) {
            // MemberToken 에 저장
            memberTokenRepository.save(MemberTokenConverter.toMemberToken(member, jwt.refreshToken(), refreshExpireAt));
        } else {
            // MemberToken Update
            findMemberToken.updateRefreshTokenAndExpireAt(jwt.refreshToken(), refreshExpireAt);
            memberTokenRepository.save(findMemberToken);
        }
        LocalDateTime accessExpireAt =
                LocalDateTime.ofEpochSecond(jwt.accessExpiredTime(), 0, ZoneOffset.UTC);


        return KakaoConverter.toKakaoOAuth2LoginResponse(jwt.accessToken(), jwt.refreshToken(), accessExpireAt, member.getStatus());
    }

    /**
     * 토큰 재발급 메서드
     *
     * 1. RefreshToken이 만료되었는지 확인 -> MemberToken 테이블 이용
     * 2. 만료되지 않았다면 액세스 토큰 발급
     * 3. 만료되었다면 재로그인 필요하다고 리턴
     */
    @Override
    @Transactional
    public MemberResDto.RefreshAccessTokenResponse refreshAccessToken(MemberReqDto.RefreshAccessTokenRequest request) {
        MemberToken findMemberToken = memberTokenRepository.findByRefreshToken(request.getRefreshToken())
                .orElseThrow(() -> new JwtHandler(ErrorCode.INVALID_REFRESH_TOKEN));

        // 만료된 리프레시 토큰일 경우 예외처리
        if (findMemberToken.getExpireAt().isBefore(LocalDateTime.now())) throw new JwtHandler(ErrorCode.EXPIRED_REFRESH_TOKEN);

        JwtResDTO.AccessToken accessToken = jwtGenerator.generateAccessToken(findMemberToken.getMember().getId(), String.valueOf(Role.MEMBER));
        LocalDateTime accessTokenExpiredAt = jwtGenerator.getExpiredAt(accessToken.accessToken());

        return MemberTokenConverter.toRefreshAccessTokenResponse(accessToken.accessToken(), accessTokenExpiredAt);
    }
}
