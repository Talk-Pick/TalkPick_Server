package talkPick.domain.member.port.out;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import talkPick.domain.member.dto.MemberResDto;

public interface MemberLikedTopicsQueryRepositoryPort {
    Page<MemberResDto.MemberLikedTopicsResDto> findMemberLikedTopics(Long memberId, Pageable pageable);
}
