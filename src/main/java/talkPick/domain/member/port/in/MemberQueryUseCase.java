package talkPick.domain.member.port.in;

import talkPick.domain.member.adapter.out.dto.MemberResDto;
import talkPick.core.common.response.CursorPageResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface MemberQueryUseCase {
    CursorPageResponse<MemberResDto.MemberLikedTopicResDto> getMemberLikedTopics(String authorization, LocalDateTime cursor, int size);
    CursorPageResponse<MemberResDto.MemberTopicResultResDto> getMemberTopicResultsByCreatedDate(String authorization, LocalDate date, LocalDateTime cursor, int size);
    MemberResDto.MemberProfileResponse getProfile(String authorization);

}
