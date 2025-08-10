package talkPick.domain.token.adapter.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import talkPick.domain.member.dto.MemberReqDto;
import talkPick.domain.member.dto.MemberResDto;

public interface MemberTokenCommandApi {
    ResponseEntity<MemberResDto.RefreshAccessTokenResponse> refreshAccessToken
            (@RequestBody MemberReqDto.RefreshAccessTokenRequest request);

}
