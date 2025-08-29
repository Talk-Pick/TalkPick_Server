package talkPick.domain.member.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.member.adapter.out.repository.MemberJpaRepository;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.port.out.MemberCommandRepositoryPort;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberCommandRepositoryAdapter implements MemberCommandRepositoryPort {
    private final MemberJpaRepository repository;

    @Override
    public Member save(Member member) {
        return repository.save(member);
    }

    @Override
    public Optional<Member> findById(Long memberId) {
        return repository.findById(memberId);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Optional<Member> findByProviderId(String providerId) {
        return repository.findByProviderId(providerId);
    }
}


