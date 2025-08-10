package talkPick.domain.token.converter;

import talkPick.domain.member.domain.Member;
import talkPick.domain.member.dto.MemberResDto;
import talkPick.domain.token.domain.MemberToken;

import java.time.LocalDateTime;

public class MemberTokenConverter {
    public static MemberToken toMemberToken(Member member, String refreshToken, LocalDateTime expireAt) {
        return MemberToken.builder()
                .member(member)
                .refreshToken(refreshToken)
                .expireAt(expireAt)
                .build();
    }

    public static MemberResDto.RefreshAccessTokenResponse toRefreshAccessTokenResponse (String accessToken, LocalDateTime accessTokenExpireAt) {
        return MemberResDto.RefreshAccessTokenResponse.builder()
                .accessToken(accessToken)
                .accessTokenExpireAt(accessTokenExpireAt)
                .build();
    }
}
