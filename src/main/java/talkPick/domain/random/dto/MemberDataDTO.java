package talkPick.domain.random.dto;

import talkPick.domain.member.domain.Member;
import talkPick.domain.member.domain.type.MBTI;

public record MemberDataDTO(
        MBTI mbti
) {
    public static MemberDataDTO from(Member member) {
        return new MemberDataDTO(member.getMbti());
    }
}