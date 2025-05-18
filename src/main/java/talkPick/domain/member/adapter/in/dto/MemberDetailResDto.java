package talkPick.domain.member.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import talkPick.domain.member.domain.Member;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
@AllArgsConstructor
public class MemberDetailResDto {
    private String email;
    private String birthDate;
    private String mbti;
    private String nickname;
    private String imageUrl;

    public static MemberDetailResDto fromEntity(Member member) {
        String formattedBirth = null;
        if (member.getBirth() != null) {
            formattedBirth = member.getBirth().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        }

        String mbtiString = null;
        if (member.getMbti() != null) {
            mbtiString = member.getMbti().toString();
        }
        return MemberDetailResDto.builder()
                .email(member.getEmail())
                .birthDate(formattedBirth)
                .mbti(mbtiString)
                .imageUrl(member.getProfileImageUrl())
                .build();
    }


}
