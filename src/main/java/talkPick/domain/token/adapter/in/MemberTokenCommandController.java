package talkPick.domain.token.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import talkPick.domain.member.dto.MemberReqDto;
import talkPick.domain.member.dto.MemberResDto;
import talkPick.domain.token.application.MemberTokenCommandUseCase;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberTokenCommandController implements MemberTokenCommandApi{
    private final MemberTokenCommandUseCase memberTokenCommandUseCase;
    @Override
    public ResponseEntity<MemberResDto.RefreshAccessTokenResponse> refreshAccessToken(MemberReqDto.RefreshAccessTokenRequest request) {
        return ResponseEntity.ok(memberTokenCommandUseCase.refreshAccessToken(request));
    }
}
