package talkPick.domain.member.port.out;

import talkPick.domain.member.domain.Member;
import talkPick.domain.member.dto.MemberResDto;

import java.time.LocalDateTime;
import java.util.List;

public interface MemberLikedTopicsQueryRepositoryPort {
    List<MemberResDto.MemberLikedTopicResDto> findMemberLikedTopics(Member member, LocalDateTime cursor, int size);
}
