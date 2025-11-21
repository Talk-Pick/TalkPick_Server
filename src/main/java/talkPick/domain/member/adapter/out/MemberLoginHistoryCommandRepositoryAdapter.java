package talkPick.domain.member.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.member.adapter.out.repository.MemberLoginHistoryJpaRepository;
import talkPick.domain.member.domain.MemberLoginHistory;
import talkPick.domain.member.port.out.MemberLoginHistoryCommandRepositoryPort;

@Component
@RequiredArgsConstructor
public class MemberLoginHistoryCommandRepositoryAdapter implements MemberLoginHistoryCommandRepositoryPort {
    private final MemberLoginHistoryJpaRepository repository;

    @Override
    public void save(MemberLoginHistory loginHistory) {
        repository.save(loginHistory);
    }

    @Override
    public void deleteByMemberId(Long memberId) {
        repository.deleteByMemberId(memberId);
    }
}


