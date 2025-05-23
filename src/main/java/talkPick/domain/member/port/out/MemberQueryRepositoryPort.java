package talkPick.domain.member.port.out;

import talkPick.domain.random.dto.MemberInfoDTO;

import java.util.Optional;

public interface MemberQueryRepositoryPort {
    Optional<MemberInfoDTO> findMemberInfoById(String memberId);
}
