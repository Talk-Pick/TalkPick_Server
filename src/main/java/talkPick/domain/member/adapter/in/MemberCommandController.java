package talkPick.domain.member.adapter.in;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import talkPick.domain.member.adapter.in.dto.MemberReqDto;
import talkPick.domain.member.adapter.out.dto.MemberResDto;
import talkPick.global.security.jwt.dto.JwtResDTO;
import talkPick.external.kakao.application.KakaoOidcService;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.dto.*;
import talkPick.domain.member.port.in.MemberCommandUseCase;
import talkPick.global.security.jwt.port.in.JwtTokenCommandUseCase;

import talkPick.global.response.ResultResponse;

/**
 * 회원 명령 관련 컨트롤러
 * 회원 가입, MBTI 설정 등의 기능을 제공합니다.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberCommandController implements MemberCommandApi {
    private final KakaoOidcService kakaoOidcService;
    private final MemberCommandUseCase memberCommandUseCase;
    private final JwtTokenCommandUseCase jwtTokenCommandUseCase;

    @Override
    public ResponseEntity<ResultResponse<JwtResDTO.Login>> joinEmailMember(
            @Valid @RequestBody MemberReqDto.MemberEmailRequest memberReqDto
           ) {
        Member findOrCreateEmailMember = memberCommandUseCase.findOrCreateEmailMember(memberReqDto);

        return ResponseEntity.ok(ResultResponse.success(jwtTokenCommandUseCase.generateToken(findOrCreateEmailMember)));
    }

    @Override
    public ResponseEntity<ResultResponse<JwtResDTO.Login>> emailLogin(
            @Valid @RequestBody MemberReqDto.MemberEmailRequest memberReqDto
           ) {
        return ResponseEntity.ok(ResultResponse.success(
                jwtTokenCommandUseCase.generateToken(memberCommandUseCase.loginEmailMember(memberReqDto))));
    }

    @Override
    public ResponseEntity<ResultResponse<JwtResDTO.Login>> kakaoOAuth2Login(
            @Valid @RequestBody MemberReqDto.KakaoOAuth2LoginRequest request) {
        // id_token 검증 후 멤버 데이터 추출
        MemberDataDto.KakaoMemberData kakaoMemberData = kakaoOidcService.verifyAndParseIdToken(request);

        // id_token 에서 추출한 데이터를 통해 멤버 조회 OR 생성
        Member findOrCreateMember = memberCommandUseCase.findOrCreateKakaoMember(kakaoMemberData);

        return ResponseEntity.ok(ResultResponse.success(jwtTokenCommandUseCase.generateToken(findOrCreateMember)));
    }

    @Override
    public ResponseEntity<ResultResponse<JwtResDTO.AccessToken>> refreshAccessToken(
            @Valid @RequestBody MemberReqDto.RefreshAccessTokenRequest request) {
        return ResponseEntity.ok(ResultResponse.success(jwtTokenCommandUseCase.refreshAccessToken(request)));
    }

    @Override
    public ResponseEntity<ResultResponse<MemberResDto.MemberSignupResponse>> signup(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @Valid @RequestBody MemberReqDto.MemberSignupRequest request) {
        return ResponseEntity.ok(ResultResponse.success(memberCommandUseCase.memberSignup(authorization, request)));
    }

    @Override
    public ResponseEntity<ResultResponse<MemberResDto.TermAgreementResponse>> termAgreement(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @Valid @RequestBody MemberReqDto.TermAgreementRequest request) {
        return ResponseEntity.ok(ResultResponse.success(memberCommandUseCase.termAgreement(authorization, request)));
    }

    @Override
    public ResponseEntity<ResultResponse<MemberResDto.ProfileUpdateResponse>> updateProfile(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody MemberReqDto.ProfileUpdateRequest request) {
        return ResponseEntity.ok(ResultResponse.success(memberCommandUseCase.updateProfile(authorization, request)));
    }

    @Override
    public ResponseEntity<ResultResponse<Void>> logout(
            @RequestHeader(value = "Authorization", required = false) String authorization){
        memberCommandUseCase.logout(authorization);
        return ResponseEntity.ok(ResultResponse.success(null));
    }

    @Override
    public ResponseEntity<ResultResponse<Void>> deleteMember(
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        memberCommandUseCase.delete(authorization);
        return ResponseEntity.ok(ResultResponse.success(null));
    }

    @Override
    public ResponseEntity<ResultResponse<Void>> changeComment(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @Valid @RequestBody MemberReqDto.TopicResultCommentChangeRequest request) {
        memberCommandUseCase.TopicResultCommentChange(authorization, request);
        return ResponseEntity.ok(ResultResponse.success(null));
    }



}