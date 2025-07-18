package talkPick.domain.member.port.out;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import talkPick.domain.member.adapter.in.dto.MemberLikedTopicsResDto;

public interface MemberLikedTopicsQueryRepositoryPort {
    public Page<MemberLikedTopicsResDto> findMemberLikedTopics(Long memberId, Pageable pageable);
}
