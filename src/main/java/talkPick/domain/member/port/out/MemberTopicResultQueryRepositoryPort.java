package talkPick.domain.member.port.out;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import talkPick.domain.member.adapter.in.dto.MemberTopicResultResDto;
import java.time.LocalDate;

public interface MemberTopicResultQueryRepositoryPort {
    public Page<MemberTopicResultResDto> findMemberTopicResults(Long memberId, LocalDate date, Pageable pageable);
}
