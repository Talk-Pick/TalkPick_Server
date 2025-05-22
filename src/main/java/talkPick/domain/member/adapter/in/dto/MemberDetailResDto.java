package talkPick.domain.member.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.domain.type.MBTI;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MemberDetailResDto {
    private String email;
    private String birthDate;
    private MBTI mbti;
    private String nickname;
    private String imageUrl;

    public static MemberDetailResDto fromEntity(Member member) {
        String formattedBirth = null;
        if (member.getBirth() != null) {
            formattedBirth = member.getBirth().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        }

        MBTI mbtiString = null;
        if (member.getMbti() != null) {
            mbtiString = member.getMbti();
        }
        return MemberDetailResDto.builder()
                .email(member.getEmail())
                .birthDate(formattedBirth)
                .mbti(mbtiString)
                .imageUrl(member.getProfileImageUrl())
                .build();
    }


}
