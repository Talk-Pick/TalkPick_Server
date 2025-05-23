package talkPick.domain.member.port.out;

import talkPick.domain.member.domain.Member;
import talkPick.domain.random.dto.MemberDataDTO;

public interface MemberQueryRepositoryPort {
    MemberDataDTO findMemberDataById(final Long memberId);
    Member findMemberById(final Long memberId);
}
