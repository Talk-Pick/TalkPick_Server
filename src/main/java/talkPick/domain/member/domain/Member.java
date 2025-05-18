package talkPick.domain.member.domain;

import jakarta.persistence.*;
import lombok.*;
import talkPick.domain.auth.Role;
import talkPick.domain.member.adapter.in.dto.KakaoUserInfo;
import talkPick.domain.member.adapter.in.dto.MemberEmailReqDTO;
import talkPick.domain.member.domain.type.Gender;
import talkPick.domain.member.domain.type.LoginType;
import talkPick.domain.member.domain.type.MBTI;
import talkPick.global.common.model.BaseTime;
import talkPick.global.common.model.TalkPickStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String kakaoId;
    private String email;
    private Role memberRole;
    private String password;
    private String name;
    private LocalDate birth;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @Enumerated(EnumType.STRING)
    private TalkPickStatus status;

    @Enumerated(EnumType.STRING)
    private MBTI mbti;

    private String profileImageUrl;

    public Member(MemberEmailReqDTO memberResDto) {
        this.email = memberResDto.getEmail();
        this.name = memberResDto.getName();
        this.password = memberResDto.getPassword();
        this.birth = memberResDto.getBirth();
        this.gender = memberResDto.getGender();
        this.mbti = memberResDto.getMbti();
        this.kakaoId = null;
        this.loginType = LoginType.EMAIL;
        this.status = null;
    }

    public Member(KakaoUserInfo kakaoUserInfo, MBTI mbti) {
        this.email = String.valueOf(kakaoUserInfo.getKakao_account().get("email"));
        this.name = String.valueOf(kakaoUserInfo.getKakao_account().get("nickname"));
        this.password = null;
        String birthStr = String.valueOf(kakaoUserInfo.getKakao_account().get("birthday"));
        this.birth = birthStr != null && !birthStr.equals("null") ?
                LocalDate.parse(birthStr, DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;

        if (String.valueOf(kakaoUserInfo.getKakao_account().get("gender")).toUpperCase().equals("MALE")) {
            this.gender = Gender.MALE;
        } else {
            this.gender = Gender.FEMALE;
        }
        this.mbti = mbti;
        this.kakaoId = kakaoUserInfo.getId();
        this.loginType = LoginType.KAKAO;
        this.status = null;
    }
}