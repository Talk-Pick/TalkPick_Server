package talkPick.domain.member.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import talkPick.domain.auth.Role;
import talkPick.domain.member.domain.type.Gender;
import talkPick.domain.member.domain.type.LoginType;
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
    private Gender gender;
    private LoginType loginType;
    private TalkPickStatus status;
}