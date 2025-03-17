package talkPick.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.adapter.out.repository.MemberJpaRepository;
import talkPick.domain.member.Member;
import talkPick.error.ErrorCode;
import talkPick.exception.MemberNotFoundException;
import talkPick.port.out.MemberQueryRepositoryPort;

@Component
@RequiredArgsConstructor
public class MemberQueryRepositoryAdaptor implements MemberQueryRepositoryPort {
    private final MemberJpaRepository memberJpaRepository;

    public Member findMemberById(final Long memberId) {
        return memberJpaRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
