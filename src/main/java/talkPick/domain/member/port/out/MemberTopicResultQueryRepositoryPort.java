package talkPick.domain.member.port.out;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import talkPick.domain.member.dto.MemberResDto;
import java.time.LocalDate;

public interface MemberTopicResultQueryRepositoryPort {
    public Page<MemberResDto.MemberTopicResultResDto> findMemberTopicResults(Long memberId, LocalDate date, Pageable pageable);
}
