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
    private String birth;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @Enumerated(EnumType.STRING)
    private TalkPickStatus status;

    @Enumerated(EnumType.STRING)
    private MBTI mbti;

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
        this.birth = String.valueOf(kakaoUserInfo.getKakao_account().get("birthday"));
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