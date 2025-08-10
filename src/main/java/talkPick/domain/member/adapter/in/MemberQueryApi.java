package talkPick.domain.member.adapter.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import talkPick.domain.member.dto.*;

import java.time.LocalDate;
import java.util.List;

public interface MemberQueryApi {
    Page<MemberResDto.MemberLikedTopicsResDto> getMemberLikedTopics(Pageable pageable);
    Page<MemberResDto.MemberTopicResultResDto> getMemberTopicResults(@RequestParam("date") LocalDate date, Pageable pageable);
    ResponseEntity<MemberResDto.ProfileResponse> getProfile(
            @RequestHeader(value = "Authorization", required = false) String authorization);
}
