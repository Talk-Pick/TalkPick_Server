package talkPick.domain.member.adapter.in;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import talkPick.domain.member.dto.MemberReqDto;
import talkPick.domain.member.dto.MemberResDto;
import talkPick.global.response.ResultResponse;

@Tag(name = "유저 API", description = "유저 관련 API 입니다.")
public interface MemberCommandApi {
    ResponseEntity<ResultResponse<MemberResDto.LoginTokenResponse>> joinEmailMember
            (@RequestBody MemberReqDto.MemberEmailReqDto memberReqDto);
    ResponseEntity<ResultResponse<MemberResDto.LoginTokenResponse>> emailLogin
            (@RequestBody MemberReqDto.MemberEmailReqDto memberReqDto);
    ResponseEntity<ResultResponse<MemberResDto.LoginTokenResponse>> kakaoOAuth2Login
            (@RequestBody MemberReqDto.KakaoOAuth2LoginRequest request);
    ResponseEntity<ResultResponse<MemberResDto.MemberSignupResponse>> signup
            (@RequestHeader(value = "Authorization", required = false) String authorization,
             @RequestBody MemberReqDto.MemberSignupRequest request);
    ResponseEntity<ResultResponse<MemberResDto.TermAgreementResponse>> termAgreement(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody MemberReqDto.TermAgreementRequest request);
    ResponseEntity<ResultResponse<MemberResDto.ProfileUpdateResponse>> updateProfile(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            MemberReqDto.ProfileUpdateRequest request);
    ResponseEntity<ResultResponse<Void>> logout(
            @RequestHeader(value = "Authorization", required = false) String authorization);
    ResponseEntity<ResultResponse<Void>> deleteMember(
            @RequestHeader(value = "Authorization", required = false) String authorization);
}
