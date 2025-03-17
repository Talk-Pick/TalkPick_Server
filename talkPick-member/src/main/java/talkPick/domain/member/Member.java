package talkPick.domain.member;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import talkPick.domain.type.Gender;
import talkPick.domain.type.LoginType;
import talkPick.model.BaseTime;
import talkPick.model.TalkPickStatus;

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
    private String password;
    private String name;
    private String birth;
    private Gender gender;
    private LoginType loginType;
    private TalkPickStatus status;
}