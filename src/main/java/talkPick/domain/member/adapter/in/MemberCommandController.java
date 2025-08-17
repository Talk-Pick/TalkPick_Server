package talkPick.domain.member.adapter.in;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import talkPick.domain.kakao.service.KakaoOidcService;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.dto.*;
import talkPick.domain.member.port.in.MemberCommandUseCase;
import talkPick.domain.token.application.MemberTokenCommandUseCase;
import talkPick.domain.member.converter.MemberConverter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import talkPick.global.response.ResultResponse;

/**
 * 회원 명령 관련 컨트롤러
 * 회원 가입, MBTI 설정 등의 기능을 제공합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@Slf4j
public class MemberCommandController implements MemberCommandApi {
    private final KakaoOidcService kakaoOidcService;
    private final MemberCommandUseCase memberCommandUseCase;
    private final MemberTokenCommandUseCase memberTokenCommandUseCase;

    @Override
    @Operation(summary = "이메일 회원가입", description = "이메일, 비밀번호 등으로 회원가입을 처리합니다. 회원가입 후 약관 동의와 추가 정보 입력이 필요합니다.")
    @PostMapping("/email/signup")
    public ResponseEntity<ResultResponse<MemberResDto.LoginTokenResponse>> joinEmailMember(
            @Parameter(description = "회원가입 요청 DTO", required = true)
            @Valid @RequestBody MemberReqDto.MemberEmailReqDto memberReqDto
           ) {
        Member findOrCreateEmailMember = memberCommandUseCase.findOrCreateEmailMember(memberReqDto);

        return ResponseEntity.ok(ResultResponse.success(memberTokenCommandUseCase.generateToken(findOrCreateEmailMember)));
    }

    @Override
    @Operation(summary = "이메일 로그인", description = "이메일, 비밀번호로 로그인을 처리합니다.")
    @PostMapping("/email/login")
    public ResponseEntity<ResultResponse<MemberResDto.LoginTokenResponse>> emailLogin(
            @Parameter(description = "로그인 요청 DTO", required = true)
            @Valid @RequestBody MemberReqDto.MemberEmailReqDto memberReqDto
           ) {
        Member member = memberCommandUseCase.loginEmailMember(memberReqDto);

        return ResponseEntity.ok(ResultResponse.success(memberTokenCommandUseCase.generateToken(member)));
    }

    @Override
    @PostMapping("/kakao/login")
    @Operation(summary = "KAKAO OAuth2 로그인 API", description = "KAKAO OAuth2 로그인 API 입니다.")
    public ResponseEntity<ResultResponse<MemberResDto.LoginTokenResponse>> kakaoOAuth2Login
            (@Valid @RequestBody MemberReqDto.KakaoOAuth2LoginRequest request) {
        // id_token 검증 후 멤버 데이터 추출
        MemberDataDto.KakaoMemberData kakaoMemberData = kakaoOidcService.verifyAndParseIdToken(request);

        // id_token 에서 추출한 데이터를 통해 멤버 조회 OR 생성
        Member findOrCreateMember = memberCommandUseCase.findOrCreateKakaoMember(kakaoMemberData);

        return ResponseEntity.ok(ResultResponse.success(memberTokenCommandUseCase.generateToken(findOrCreateMember)));
    }

    @Override
    @PatchMapping("/signup")
    @Operation(summary = "회원가입 완료 API", description = "회원의 추가 정보(닉네임, MBTI, 성별, 생년월일, 프로필 이미지)를 입력하여 회원가입을 완료하는 API입니다.")
    public ResponseEntity<ResultResponse<MemberResDto.MemberSignupResponse>> signup(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @Valid @RequestBody MemberReqDto.MemberSignupRequest request) {
        return ResponseEntity.ok(ResultResponse.success(memberCommandUseCase.memberSignup(authorization, request)));
    }

    @Override
    @PostMapping("/term")
    @Operation(summary = "약관 동의 API", description = "회원이 약관에 동의하는 API입니다.")
    public ResponseEntity<ResultResponse<MemberResDto.TermAgreementResponse>> termAgreement(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @Valid @RequestBody MemberReqDto.TermAgreementRequest request) {
        return ResponseEntity.ok(ResultResponse.success(memberCommandUseCase.termAgreement(authorization, request)));
    }

    @Override
    @PatchMapping("/me")
    @Operation(summary = "프로필 수정 API", description = "회원의 프로필 정보(닉네임, 생년월일, MBTI)를 수정하는 API입니다.")
    public ResponseEntity<ResultResponse<MemberResDto.ProfileUpdateResponse>> updateProfile(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            MemberReqDto.ProfileUpdateRequest request) {
        return ResponseEntity.ok(ResultResponse.success(memberCommandUseCase.updateProfile(authorization, request)));
    }

    @Override
    @DeleteMapping("/logout")
    @Operation(summary = "로그아웃 API", description = "로그아웃 API입니다.")
    public ResponseEntity<ResultResponse<Void>> logout(
            @RequestHeader(value = "Authorization", required = false) String authorization){
        memberCommandUseCase.logout(authorization);
        return ResponseEntity.ok(ResultResponse.success(null));
    }

    @PatchMapping("/delete")
    @Operation(summary = "계정 탈퇴 API", description = "계정 탈퇴 API입니다.")
    public ResponseEntity<ResultResponse<Void>> deleteMember(
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        memberCommandUseCase.delete(authorization);
        return ResponseEntity.ok(ResultResponse.success(null));
    }



}