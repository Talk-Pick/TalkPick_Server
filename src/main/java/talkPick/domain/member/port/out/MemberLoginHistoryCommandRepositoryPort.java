package talkPick.domain.member.port.out;

import talkPick.domain.member.domain.MemberLoginHistory;

public interface MemberLoginHistoryCommandRepositoryPort {
    void save(MemberLoginHistory loginHistory);
    void deleteByMemberId(Long memberId);
}


