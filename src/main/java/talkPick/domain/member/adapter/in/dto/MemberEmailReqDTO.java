package talkPick.domain.member.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import talkPick.domain.member.domain.type.Gender;
import talkPick.domain.member.domain.type.MBTI;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberEmailReqDTO {
    private String email;
    private String password;
    private String name;
    private String birth;
    private Gender gender;
    private MBTI mbti;
}
