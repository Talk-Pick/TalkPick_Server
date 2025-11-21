package talkPick.domain.member.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.member.adapter.out.repository.MemberJpaRepository;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.port.out.MemberQueryRepositoryPort;
import talkPick.domain.random.dto.MemberDataDTO;
import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.handler.MemberExceptionHandler;

@Component
@RequiredArgsConstructor
public class MemberQueryRepositoryAdapter implements MemberQueryRepositoryPort {
    private final MemberJpaRepository memberJpaRepository;

    @Override
    public MemberDataDTO findMemberDataById(final Long memberId) {
        var member = findMemberById(memberId);
        return MemberDataDTO.from(member);
    }

    @Override
    public Member findMemberById(final Long memberId) {
        return memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new MemberExceptionHandler(ErrorCode.MEMBER_NOT_FOUND));
    }

    @Override
    public java.util.Optional<Member> findByEmail(String email) {
        return memberJpaRepository.findByEmail(email);
    }

    @Override
    public java.util.Optional<Member> findByProviderId(String providerId) {
        return memberJpaRepository.findByProviderId(providerId);
    }
}