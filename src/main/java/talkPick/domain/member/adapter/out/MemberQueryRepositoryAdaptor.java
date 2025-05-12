package talkPick.domain.member.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.member.adapter.out.repository.MemberJpaRepository;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.port.out.MemberQueryRepositoryPort;
import talkPick.global.error.ErrorCode;
import talkPick.global.error.exception.member.MemberNotFoundException;

@Component
@RequiredArgsConstructor
public class MemberQueryRepositoryAdaptor implements MemberQueryRepositoryPort {
    private final MemberJpaRepository memberJpaRepository;

    public Member findMemberById(final Long memberId) {
        return memberJpaRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
