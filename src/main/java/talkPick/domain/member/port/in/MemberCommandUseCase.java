package talkPick.domain.member.port.in;

import talkPick.domain.member.adapter.in.dto.MemberEmailReqDto;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.domain.type.MBTI;

public interface MemberCommandUseCase {
    void setEmailMember(MemberEmailReqDto memberReqDto);
    Member setKakaoMember(Member member);
    Member updateMemberMbti(Long memberId, MBTI mbti);
}
