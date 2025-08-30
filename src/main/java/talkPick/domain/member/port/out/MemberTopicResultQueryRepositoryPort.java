package talkPick.domain.member.port.out;

import talkPick.domain.member.adapter.out.dto.MemberResDto;
import talkPick.domain.member.domain.Member;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MemberTopicResultQueryRepositoryPort {
    List<MemberResDto.MemberTopicResultResDto> findMemberTopicResults(Member member, LocalDate date, LocalDateTime cursor, int size);
}

