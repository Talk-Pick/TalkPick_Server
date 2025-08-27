package talkPick.domain.member.adapter.in;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import talkPick.domain.member.adapter.out.dto.MemberResDto;
import talkPick.domain.member.port.in.MemberQueryUseCase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import talkPick.global.response.CursorPageResponse;
import talkPick.global.response.ResultResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberQueryController implements MemberQueryApi {
    private final MemberQueryUseCase memberQueryUseCase;

    @Override
    public ResponseEntity<ResultResponse<MemberResDto.ProfileResponse>> getProfile(
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        return ResponseEntity.ok(ResultResponse.success(memberQueryUseCase.getProfile(authorization)));
    }

    @Override
    public ResponseEntity<ResultResponse<CursorPageResponse<MemberResDto.MemberLikedTopicResDto>>> getMemberLikedTopics(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam(value = "cursor", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime cursor,
            @RequestParam(value = "size", defaultValue = "6") int size) {
        return ResponseEntity.ok(ResultResponse.success(memberQueryUseCase.getMemberLikedTopics(authorization, cursor, size)));
    }


    @Override
    public ResponseEntity<ResultResponse<CursorPageResponse<MemberResDto.MemberTopicResultResDto>>> getMemberTopicResults(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
            ) {
        memberQueryUseCase.getMemberTopicResultsByCreatedDate(authorization, date);
        return null;
    }
}
