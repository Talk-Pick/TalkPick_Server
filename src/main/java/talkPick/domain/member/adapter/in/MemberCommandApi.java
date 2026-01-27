package talkPick.domain.member.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import talkPick.domain.member.adapter.in.dto.MemberReqDto;
import talkPick.domain.member.adapter.out.dto.MemberResDto;
import talkPick.domain.auth.adapter.out.dto.TokenResponse;
@RequestMapping("/api/v1/members")
@Tag(name = "유저 API", description = "유저 관련 API 입니다.")
public interface MemberCommandApi {
    @PostMapping("/kakao/login")
    @Operation(summary = "KAKAO OAuth2 로그인 API", description = "KAKAO OAuth2 로그인 API 입니다.")
    TokenResponse.Login kakaoOAuth2Login(
            @Valid @RequestBody MemberReqDto.OAuth2LoginRequest request, HttpServletResponse response);

    @PatchMapping("/apple/login")
    @Operation(summary = "APPLE Oauth2 로그인 API", description = "APPLE OAuth2 로그인 API 입니다.")
    TokenResponse.Login appleOauth2Login (@Valid @RequestBody MemberReqDto.OAuth2LoginRequest request, HttpServletResponse response);

    @PostMapping("/google/login")
    @Operation(summary = "GOOGLE OAuth2 로그인 API", description = "GOOGLE OAuth2 로그인 API 입니다.")
    TokenResponse.Login googleOauth2Login(
            @Valid @RequestBody MemberReqDto.OAuth2LoginRequest request, HttpServletResponse response);

    @PostMapping("/{provider}/reactivate")
    @Operation(summary = "계정 복구 API", description = "탈퇴한 계정(kakao/apple/google)을 복구하고 로그인합니다. provider: 'kakao', 'apple' 또는 'google'")
    TokenResponse.Login reactivateMember(
            @Parameter(description = "kakao, apple 또는 google", example = "kakao") @PathVariable("provider") String provider,
            @Valid @RequestBody MemberReqDto.OAuth2LoginRequest request, HttpServletResponse response);

    @PostMapping("/token/refresh")
    @Operation(summary = "액세스 토큰 재발급", description = "쿠키에 담긴 리프레시 토큰으로 액세스 토큰을 재발급합니다.")
    TokenResponse.AccessToken refreshAccessToken(
            @CookieValue("refreshToken") String refreshToken);

    @PatchMapping("/signup")
    @Operation(summary = "회원가입 완료 API (OAuth 공통 - 회원가입 시 마지막 단계)", description = "회원의 추가 정보(닉네임, MBTI, 성별, 생년월일, 프로필 이미지)를 입력하여 회원가입을 완료하는 API입니다.")
    MemberResDto.MemberSignupResponse signup(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @Valid @RequestBody MemberReqDto.MemberSignupRequest request);

    @PostMapping("/term")
    @Operation(summary = "약관 동의 API (회원가입 완료 API 전에 수행)", description = "회원이 약관에 동의하는 API입니다.")
    MemberResDto.TermAgreementResponse termAgreement(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @Valid @RequestBody MemberReqDto.TermAgreementRequest request);

    @PatchMapping("/me")
    @Operation(summary = "프로필 정보 수정 API", description = "회원의 프로필 정보를 수정하는 API입니다.")
    MemberResDto.MemberProfileResponse updateProfile(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody @Valid MemberReqDto.ProfileUpdateRequest request);

    @DeleteMapping("/logout")
    @Operation(summary = "로그아웃 API", description = "로그아웃 API입니다.")
    void logout(
            @RequestHeader(value = "Authorization", required = false) String authorization);

    @PatchMapping("/delete")
    @Operation(summary = "계정 탈퇴 API", description = "계정 탈퇴 API입니다.")
    void deleteMember(
            @RequestHeader(value = "Authorization", required = false) String authorization);

    @PatchMapping("/topic-results")
    @Operation(summary = "캘린더 조회 토픽 코멘트 수정 API", description = "캘린더 조회 토픽의 코멘트를 수정하는 API입니다.")
    void changeComment(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @Valid @RequestBody MemberReqDto.TopicResultCommentChangeRequest request
    );
}