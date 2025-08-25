package talkPick.domain.member.port.out;

import talkPick.domain.member.domain.Member;
import talkPick.domain.random.dto.MemberDataDTO;
import java.util.Optional;

public interface MemberQueryRepositoryPort {
    MemberDataDTO findMemberDataById(final Long memberId);
    Member findMemberById(final Long memberId);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByProviderId(String providerId);
}
