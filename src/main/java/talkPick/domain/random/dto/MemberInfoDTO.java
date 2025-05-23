package talkPick.domain.random.dto;

import talkPick.domain.member.domain.Member;
import talkPick.domain.member.domain.type.Gender;
import talkPick.domain.member.domain.type.MBTI;

public record MemberInfoDTO(
        MBTI mbti,
        Gender gender,
        int age
) {
//    public static MemberInfoDTO from(Member member) {
//        return new MemberInfoDTO(member.getMbti(), member.getGender(), ;
//    }
}