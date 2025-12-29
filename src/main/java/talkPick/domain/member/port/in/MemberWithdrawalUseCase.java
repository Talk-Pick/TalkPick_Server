package talkPick.domain.member.port.in;

public interface MemberWithdrawalUseCase {
    void withdraw(String authorization);
    void hardDelete(Long memberId);
}
