package talkPick.domain.member.adapter.out.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.domain.type.Gender;
import talkPick.domain.member.domain.type.MBTI;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberEmailResDTO {
    private String email;
    private String name;
    private String birth;
    private Gender gender;
    private MBTI mbti;

    public MemberEmailResDTO(Member member) {
        this.email = member.getEmail();
        this.name = member.getName();
        this.birth = member.getBirth();
        this.gender = member.getGender();
        this.mbti = member.getMbti();
    }
}
