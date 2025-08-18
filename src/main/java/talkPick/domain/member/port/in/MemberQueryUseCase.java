package talkPick.domain.member.port.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import talkPick.domain.member.dto.*;
import talkPick.global.response.CursorPageResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface MemberQueryUseCase {
    CursorPageResponse<MemberResDto.MemberLikedTopicResDto> getMemberLikedTopics(String authorization, LocalDateTime cursor, int size);
//    Page<MemberResDto.MemberTopicResultResDto> getMemberTopicResultsByCreatedDate(String authorization, LocalDate date);
    MemberResDto.ProfileResponse getProfile(String authorization);

}
