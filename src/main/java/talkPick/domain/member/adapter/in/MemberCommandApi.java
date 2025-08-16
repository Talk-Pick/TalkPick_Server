package talkPick.domain.member.adapter.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import talkPick.domain.member.dto.MemberReqDto;
import talkPick.domain.member.dto.MemberResDto;

public interface MemberCommandApi {
    ResponseEntity<MemberResDto.LoginTokenResponse> joinEmailMember
            (@RequestBody MemberReqDto.MemberEmailReqDto memberReqDto);
    ResponseEntity<MemberResDto.LoginTokenResponse> kakaoOAuth2Login
            (@RequestBody MemberReqDto.KakaoOAuth2LoginRequest request);
    ResponseEntity<MemberResDto.MemberSignupResponse> signup
            (@RequestHeader(value = "Authorization", required = false) String authorization,
             @RequestBody MemberReqDto.MemberSignupRequest request);
    ResponseEntity<MemberResDto.TermAgreementResponse> termAgreement(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody MemberReqDto.TermAgreementRequest request);
    ResponseEntity<MemberResDto.ProfileUpdateResponse> updateProfile(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            MemberReqDto.ProfileUpdateRequest request);
    ResponseEntity<Void> logout(
            @RequestHeader(value = "Authorization", required = false) String authorization);
    ResponseEntity<Void> deleteMember(
            @RequestHeader(value = "Authorization", required = false) String authorization);
}
