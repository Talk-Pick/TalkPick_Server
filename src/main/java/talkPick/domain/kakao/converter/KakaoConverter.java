package talkPick.domain.kakao.converter;

import io.jsonwebtoken.Claims;
import talkPick.domain.member.dto.MemberDataDto;
import talkPick.domain.member.dto.MemberResDto;

import java.time.LocalDateTime;

public class KakaoConverter {
    public static MemberDataDto.KakaoMemberData toKakaoMemberData(Claims claims) {
        return MemberDataDto.KakaoMemberData.builder()
                .sub(claims.getSubject())
                .email(claims.get("email", String.class))
                .build();
    }

    public static MemberResDto.LoginTokenResponse toKakaoOAuth2LoginResponse(String accessToken, String refreshToken, LocalDateTime accessTokenExpireAt) {
        return MemberResDto.LoginTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpireAt(accessTokenExpireAt)
                .build();
    }
}
