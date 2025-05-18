package talkPick.domain.member.port.in;

import talkPick.domain.member.adapter.in.dto.MemberEmailReqDTO;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.domain.type.MBTI;

public interface MemberCommandUseCase {
    void setEmailMember(MemberEmailReqDTO memberReqDto);
    Member setKakaoMember(Member member);
    Member updateMemberMbti(Long memberId, MBTI mbti);
}
