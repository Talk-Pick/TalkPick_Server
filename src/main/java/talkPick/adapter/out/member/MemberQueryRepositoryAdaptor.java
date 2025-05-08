package talkPick.adapter.out.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.adapter.out.member.repository.MemberJpaRepository;
import talkPick.common.error.ErrorCode;
import talkPick.domain.member.Member;
import talkPick.exception.member.MemberNotFoundException;
import talkPick.port.out.member.MemberQueryRepositoryPort;

@Component
@RequiredArgsConstructor
public class MemberQueryRepositoryAdaptor implements MemberQueryRepositoryPort {
    private final MemberJpaRepository memberJpaRepository;

    public Member findMemberById(final Long memberId) {
        return memberJpaRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
