package talkPick.domain.random.dto;

import talkPick.domain.member.domain.Member;
import talkPick.domain.member.domain.type.Gender;
import talkPick.domain.member.domain.type.MBTI;

import java.time.LocalDate;
import java.time.Period;

public record MemberDataDTO(
        MBTI mbti,
        Gender gender,
        Integer age
) {
    public static MemberDataDTO from(Member member) {
        return new MemberDataDTO(member.getMbti(), member.getGender(), calculateAge(member.getBirth()));
    }

    private static int calculateAge(LocalDate birth) {
        if (birth == null) return 0;
        return Period.between(birth, LocalDate.now()).getYears();
    }
}