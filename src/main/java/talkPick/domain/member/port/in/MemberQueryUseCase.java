package talkPick.domain.member.port.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import talkPick.domain.member.dto.*;
import talkPick.domain.member.domain.Member;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MemberQueryUseCase {
    Page<MemberResDto.MemberLikedTopicsResDto> getMemberLikedTopics(Long memberId, Pageable pageable);
    Page<MemberResDto.MemberTopicResultResDto> getMemberTopicResultsByCreatedDate(Long memberId, LocalDate date, Pageable pageable);
    MemberResDto.ProfileResponse getProfile(String authorization);

}
