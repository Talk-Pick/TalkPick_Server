package talkPick.domain.member.domain.profile;

import jakarta.persistence.*;
import lombok.*;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.domain.type.MBTI;
import talkPick.global.common.model.BaseTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "member_id", unique = true)
    private Member member;

    @Enumerated(EnumType.STRING)
    private MBTI mbti;

    public Profile(Member member, MBTI mbti) {
        this.member = member;
        this.mbti = mbti;
    }
}