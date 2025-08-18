package talkPick.domain.member.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import talkPick.domain.member.dto.MemberReqDto;
import talkPick.domain.member.dto.MemberResDto;
import talkPick.global.response.ResultResponse;

@RequestMapping("/api/v1/members")
@Tag(name = "유저 API", description = "유저 관련 API 입니다.")
public interface MemberCommandApi {
    @PostMapping("/email/signup")
    @Operation(summary = "이메일 회원가입", description = "이메일, 비밀번호 등으로 회원가입을 처리합니다. 회원가입 후 약관 동의와 추가 정보 입력이 필요합니다.")
    ResponseEntity<ResultResponse<MemberResDto.LoginTokenResponse>> joinEmailMember(
            @Parameter(description = "회원가입 요청 DTO", required = true)
            @Valid @RequestBody MemberReqDto.MemberEmailReqDto memberReqDto);

    @PostMapping("/email/login")
    @Operation(summary = "이메일 로그인", description = "이메일, 비밀번호로 로그인을 처리합니다.")
    ResponseEntity<ResultResponse<MemberResDto.LoginTokenResponse>> emailLogin(
            @Parameter(description = "로그인 요청 DTO", required = true)
            @Valid @RequestBody MemberReqDto.MemberEmailReqDto memberReqDto);

    @PostMapping("/kakao/login")
    @Operation(summary = "KAKAO OAuth2 로그인 API", description = "KAKAO OAuth2 로그인 API 입니다.")
    ResponseEntity<ResultResponse<MemberResDto.LoginTokenResponse>> kakaoOAuth2Login(
            @Valid @RequestBody MemberReqDto.KakaoOAuth2LoginRequest request);

    @PatchMapping("/signup")
    @Operation(summary = "회원가입 완료 API (이메일, OAuth 공통 - 회원가입 시 마지막 단계)", description = "회원의 추가 정보(닉네임, MBTI, 성별, 생년월일, 프로필 이미지)를 입력하여 회원가입을 완료하는 API입니다.")
    ResponseEntity<ResultResponse<MemberResDto.MemberSignupResponse>> signup(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @Valid @RequestBody MemberReqDto.MemberSignupRequest request);

    @PostMapping("/term")
    @Operation(summary = "약관 동의 API (회원가입 완료 API 전에 수행)", description = "회원이 약관에 동의하는 API입니다.")
    ResponseEntity<ResultResponse<MemberResDto.TermAgreementResponse>> termAgreement(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @Valid @RequestBody MemberReqDto.TermAgreementRequest request);

    @PatchMapping("/me")
    @Operation(summary = "프로필 수정 API", description = "회원의 프로필 정보(닉네임, 성별, 생년월일, MBTI)를 수정하는 API입니다.")
    ResponseEntity<ResultResponse<MemberResDto.ProfileUpdateResponse>> updateProfile(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @Valid MemberReqDto.ProfileUpdateRequest request);

    @DeleteMapping("/logout")
    @Operation(summary = "로그아웃 API", description = "로그아웃 API입니다.")
    ResponseEntity<ResultResponse<Void>> logout(
            @RequestHeader(value = "Authorization", required = false) String authorization);

    @PatchMapping("/delete")
    @Operation(summary = "계정 탈퇴 API", description = "계정 탈퇴 API입니다.")
    ResponseEntity<ResultResponse<Void>> deleteMember(
            @RequestHeader(value = "Authorization", required = false) String authorization);
}
