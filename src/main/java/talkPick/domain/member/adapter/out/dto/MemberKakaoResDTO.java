package talkPick.domain.member.adapter.out.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.domain.type.Gender;
import talkPick.domain.member.domain.type.MBTI;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberKakaoResDTO {
    private String kakaoId;
    private String email;
    @JsonProperty("nickname")
    private String name;
    private LocalDate birth;
    private Gender gender;
    private MBTI mbti;

    public MemberKakaoResDTO(Member member) {
        this.email = member.getEmail();
        this.name = member.getName();
        this.birth = member.getBirth();
        this.gender = member.getGender();
        this.mbti = member.getMbti();
        this.kakaoId = member.getKakaoId();
    }

    public void setKakaoId(Long kakaoId) {
        if (kakaoId != null) {
            this.kakaoId = String.valueOf(kakaoId);
        }
    }

}
