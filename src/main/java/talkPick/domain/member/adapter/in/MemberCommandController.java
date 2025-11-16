package talkPick.domain.member.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import talkPick.domain.member.adapter.in.dto.MemberReqDto;
import talkPick.domain.member.adapter.out.dto.MemberResDto;
import talkPick.domain.member.domain.type.LoginType;
import talkPick.external.apple.application.AppleOidcService;
import talkPick.external.apple.port.in.AppleOidcUsecase;
import talkPick.external.kakao.port.in.KakaoOidcUsecase;
import talkPick.global.security.jwt.dto.JwtResDTO;
import talkPick.external.kakao.application.KakaoOidcService;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.dto.*;
import talkPick.domain.member.port.in.MemberCommandUseCase;
import talkPick.global.security.jwt.port.in.JwtTokenCommandUseCase;

/**
 * 회원 명령 관련 컨트롤러
 * 회원 가입, MBTI 설정 등의 기능을 제공합니다.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberCommandController implements MemberCommandApi {
    private final KakaoOidcUsecase kakaoOidcService;
    private final AppleOidcUsecase appleOidcService;
    private final MemberCommandUseCase memberCommandUseCase;
    private final JwtTokenCommandUseCase jwtTokenCommandUseCase;

//    @Override
//    public JwtResDTO.Login joinEmailMember(
//            @Valid @RequestBody MemberReqDto.MemberEmailRequest memberReqDto
//    ) {
//        Member member = memberCommandUseCase.findOrCreateEmailMember(memberReqDto);
//        return jwtTokenCommandUseCase.generateToken(member);
//    }
//
//    @Override
//    public JwtResDTO.Login emailLogin(
//            @Valid @RequestBody MemberReqDto.MemberEmailRequest memberReqDto
//    ) {
//        Member member = memberCommandUseCase.loginEmailMember(memberReqDto);
//        return jwtTokenCommandUseCase.generateToken(member);
//    }

    @Override
    public JwtResDTO.Login kakaoOAuth2Login(
            @Valid @RequestBody MemberReqDto.OAuth2LoginRequest request
    ) {
        MemberDataDto.MemberData kakaoMemberData = kakaoOidcService.verifyAndParseIdToken(request);
        Member member = memberCommandUseCase.findOrCreateMember(kakaoMemberData, LoginType.KAKAO);
        return jwtTokenCommandUseCase.generateToken(member);
    }

    public JwtResDTO.Login appleOauth2Login (@Valid @RequestBody MemberReqDto.OAuth2LoginRequest request) {
        MemberDataDto.MemberData appleMemberData = appleOidcService.verifyAndParseIdToken(request);
        Member member = memberCommandUseCase.findOrCreateMember(appleMemberData, LoginType.APPLE);
        return jwtTokenCommandUseCase.generateToken(member);
    }

    @Override
    public JwtResDTO.AccessToken refreshAccessToken(
            @Valid @RequestBody MemberReqDto.RefreshAccessTokenRequest request
    ) {
        return jwtTokenCommandUseCase.refreshAccessToken(request);
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
    public MemberResDto.ProfileUpdateResponse updateProfile(
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
        memberCommandUseCase.delete(authorization);
    }

    @Override
    public void changeComment(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @Valid @RequestBody MemberReqDto.TopicResultCommentChangeRequest request) {
        memberCommandUseCase.TopicResultCommentChange(authorization, request);
    }



}