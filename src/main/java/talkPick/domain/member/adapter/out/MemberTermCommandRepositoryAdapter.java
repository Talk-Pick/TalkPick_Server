package talkPick.domain.member.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.member.adapter.out.repository.MemberTermJpaRepository;
import talkPick.domain.member.domain.mapping.MemberTerm;
import talkPick.domain.member.port.out.MemberTermCommandRepositoryPort;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberTermCommandRepositoryAdapter implements MemberTermCommandRepositoryPort {
    private final MemberTermJpaRepository repository;

    @Override
    public Optional<MemberTerm> findByMemberIdAndTermId(Long memberId, Long termId) {
        return repository.findByMemberIdAndTermId(memberId, termId);
    }

    @Override
    public MemberTerm save(MemberTerm memberTerm) {
        return repository.save(memberTerm);
    }

    @Override
    public void deleteByMemberId(Long memberId) {
        repository.deleteByMemberId(memberId);
    }
}


