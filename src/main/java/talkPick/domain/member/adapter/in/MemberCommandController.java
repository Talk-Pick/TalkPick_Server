package talkPick.domain.member.adapter.in;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import talkPick.domain.member.adapter.in.dto.MemberReqDto;
import talkPick.domain.member.adapter.out.dto.MemberResDto;
import talkPick.domain.member.domain.type.LoginType;
import talkPick.external.apple.port.in.AppleOidcUsecase;
import talkPick.external.google.port.in.GoogleOidcUsecase;
import talkPick.external.kakao.port.in.KakaoOidcUsecase;
import talkPick.domain.auth.adapter.out.dto.TokenResponse;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.dto.*;
import talkPick.domain.member.port.in.MemberCommandUseCase;
import talkPick.domain.member.port.in.MemberWithdrawalUseCase;
import talkPick.domain.auth.port.in.GenerateTokenUseCase;
import talkPick.domain.auth.port.in.RefreshTokenUseCase;

/**
 * 회원 명령 관련 컨트롤러
 * 회원 가입, MBTI 설정 등의 기능을 제공합니다.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/members")
public class MemberCommandController implements MemberCommandApi {
    private final KakaoOidcUsecase kakaoOidcService;
    private final AppleOidcUsecase appleOidcService;
    private final GoogleOidcUsecase googleOidcService;
    private final MemberCommandUseCase memberCommandUseCase;
    private final MemberWithdrawalUseCase memberWithdrawalUseCase;
    private final GenerateTokenUseCase generateTokenUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;


    @Override
    public TokenResponse.Login kakaoOAuth2Login(
            @Valid @RequestBody MemberReqDto.OAuth2LoginRequest request, HttpServletResponse response
    ) {
        MemberDataDto.MemberData kakaoMemberData = kakaoOidcService.verifyAndParseIdToken(request);
        Member member = memberCommandUseCase.findOrCreateMember(kakaoMemberData, LoginType.KAKAO);
        TokenResponse.GeneratedTokens generatedTokens = generateTokenUseCase.generateToken(member);

        setRefreshTokenCookie(response, generatedTokens.refreshToken(), generatedTokens.refreshExpiredTime());

        return TokenResponse.Login.of(
                member.getId(),
                member.getMemberRole().toString(),
                generatedTokens.accessToken(),
                generatedTokens.accessExpiredTime()
        );
    }

    @Override
    public TokenResponse.Login appleOauth2Login (@Valid @RequestBody MemberReqDto.OAuth2LoginRequest request, HttpServletResponse response) {
        MemberDataDto.MemberData appleMemberData = appleOidcService.verifyAndParseIdToken(request);
        Member member = memberCommandUseCase.findOrCreateMember(appleMemberData, LoginType.APPLE);
        TokenResponse.GeneratedTokens generatedTokens = generateTokenUseCase.generateToken(member);

        setRefreshTokenCookie(response, generatedTokens.refreshToken(), generatedTokens.refreshExpiredTime());

        return TokenResponse.Login.of(
                member.getId(),
                member.getMemberRole().toString(),
                generatedTokens.accessToken(),
                generatedTokens.accessExpiredTime()
        );
    }

    @Override
    public TokenResponse.Login googleOauth2Login(
            @Valid @RequestBody MemberReqDto.OAuth2LoginRequest request, HttpServletResponse response
    ) {
        MemberDataDto.MemberData googleMemberData = googleOidcService.verifyAndParseIdToken(request);
        Member member = memberCommandUseCase.findOrCreateMember(googleMemberData, LoginType.GOOGLE);
        TokenResponse.GeneratedTokens generatedTokens = generateTokenUseCase.generateToken(member);

        setRefreshTokenCookie(response, generatedTokens.refreshToken(), generatedTokens.refreshExpiredTime());

        return TokenResponse.Login.of(
                member.getId(),
                member.getMemberRole().toString(),
                generatedTokens.accessToken(),
                generatedTokens.accessExpiredTime()
        );
    }

    @Override
    public TokenResponse.Login reactivateMember(
            @PathVariable("provider") String provider,
            @Valid @RequestBody MemberReqDto.OAuth2LoginRequest request, HttpServletResponse response
    ) {
        MemberDataDto.MemberData memberData;
        LoginType loginType;

        if ("kakao".equalsIgnoreCase(provider)) {
            memberData = kakaoOidcService.verifyAndParseIdToken(request);
            loginType = LoginType.KAKAO;
        } else if ("apple".equalsIgnoreCase(provider)) {
            memberData = appleOidcService.verifyAndParseIdToken(request);
            loginType = LoginType.APPLE;
        } else if ("google".equalsIgnoreCase(provider)) {
            memberData = googleOidcService.verifyAndParseIdToken(request);
            loginType = LoginType.GOOGLE;
        } else {
            throw new IllegalArgumentException("지원하지 않는 Provider입니다: " + provider);
        }

        Member member = memberCommandUseCase.reactivateMember(memberData, loginType);
        TokenResponse.GeneratedTokens generatedTokens = generateTokenUseCase.generateToken(member);

        setRefreshTokenCookie(response, generatedTokens.refreshToken(), generatedTokens.refreshExpiredTime());

        return TokenResponse.Login.of(
                member.getId(),
                member.getMemberRole().toString(),
                generatedTokens.accessToken(),
                generatedTokens.accessExpiredTime()
        );
    }

    // refresh token을 HttpOnly 쿠키로 설정하는 헬퍼 메서드
    private void setRefreshTokenCookie(HttpServletResponse response, String refreshToken, Long refreshExpiredTime) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true); // HTTPS를 사용하는 프로덕션 환경에서는 true로 설정
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(refreshExpiredTime.intValue());
        response.addCookie(refreshTokenCookie);
    }

    @Override
    public TokenResponse.AccessToken refreshAccessToken(
            @CookieValue("refreshToken") String refreshToken
    ) {
        return refreshTokenUseCase.refreshAccessToken(refreshToken);
    }

    @Override
    public MemberResDto.MemberSignupResponse signup(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @Valid @RequestBody MemberReqDto.MemberSignupRequest request
    ) {
        return memberCommandUseCase.memberSignup(authorization, request);
    }

    @Override
    public MemberResDto.TermAgreementResponse termAgreement(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @Valid @RequestBody MemberReqDto.TermAgreementRequest request
    ) {
        return memberCommandUseCase.termAgreement(authorization, request);
    }

    @Override
    public MemberResDto.MemberProfileResponse updateProfile(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            MemberReqDto.ProfileUpdateRequest request
    ) {
        return memberCommandUseCase.updateProfile(authorization, request);
    }

    @Override
    public void logout(
            @RequestHeader(value = "Authorization", required = false) String authorization
    ) {
        memberCommandUseCase.logout(authorization);
    }

    @Override
    public void deleteMember(
            @RequestHeader(value = "Authorization", required = false) String authorization
    ) {
        // Facade 서비스 호출로 변경
        memberWithdrawalUseCase.withdraw(authorization);
    }

    @Override
    public void changeComment(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @Valid @RequestBody MemberReqDto.TopicResultCommentChangeRequest request) {
        memberCommandUseCase.TopicResultCommentChange(authorization, request);
    }

}
