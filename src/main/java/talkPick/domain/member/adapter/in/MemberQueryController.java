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

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberQueryController implements MemberQueryApi {
    private final MemberQueryUseCase memberQueryUseCase;

    @Override
    public MemberResDto.ProfileResponse getProfile(
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        return memberQueryUseCase.getProfile(authorization);
    }

    @Override
    public CursorPageResponse<MemberResDto.MemberLikedTopicResDto> getMemberLikedTopics(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam(value = "cursor", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime cursor,
            @RequestParam(value = "size", defaultValue = "6") int size) {
        return memberQueryUseCase.getMemberLikedTopics(authorization, cursor, size);
    }


    @Override
    public ResponseEntity<ResultResponse<CursorPageResponse<MemberResDto.MemberTopicResultResDto>>> getMemberTopicResults(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(value = "cursor", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime cursor,
            @RequestParam(value = "size", defaultValue = "6") int size
            ) {
        CursorPageResponse<MemberResDto.MemberTopicResultResDto> result =
                memberQueryUseCase.getMemberTopicResultsByCreatedDate(authorization, date, cursor, size);
        return ResponseEntity.ok(ResultResponse.success(result));
    }
}
