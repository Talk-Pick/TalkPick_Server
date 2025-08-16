package talkPick.domain.member.domain;

import jakarta.persistence.*;
import lombok.*;
import talkPick.domain.admin.domain.type.Role;
import talkPick.domain.member.domain.type.Gender;
import talkPick.domain.member.domain.type.LoginType;
import talkPick.domain.member.domain.type.MBTI;
import talkPick.global.model.BaseTime;
import talkPick.global.model.TalkPickStatus;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(length = 100, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(length = 6, nullable = false)
    private Role memberRole;

    @Column(length = 255)
    private String password;

    @Column(length = 25, nullable = false)
    private String nickname;

    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private TalkPickStatus status;


    @Enumerated(EnumType.STRING)
    private MBTI mbti;

    @Column(length = 255, nullable = false)
    private String profileImageUrl;

    @Column(length = 255)
    private String providerId;

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateBirth(LocalDate birth) {
        this.birth = birth;
    }

    public void updateGender(Gender gender) {
        this.gender = gender;
    }

    public void updateProfileImgUrl(String profileImgUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void updateMbti(MBTI mbti) {this.mbti = mbti;}

    public void updateStatus(TalkPickStatus talkPickStatus) {this.status = talkPickStatus;}


}
