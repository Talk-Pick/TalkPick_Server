package talkPick.domain.member.domain;

import jakarta.persistence.*;
import lombok.*;
import talkPick.domain.member.domain.type.LoginType;
import talkPick.domain.member.domain.type.MBTI;
import talkPick.domain.member.domain.type.Role;
import talkPick.core.common.model.BaseTime;
import talkPick.core.common.model.TalkPickStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "member_id",
            columnDefinition = "BIGINT COMMENT '회원 고유 PK'"
    )
    private Long id;

    @Column(
            length = 100,
            nullable = false,
            columnDefinition = "VARCHAR(100) COMMENT '이메일(로그인 아이디)'"
    )
    private String email;

    @Column(
            length = 100,
            columnDefinition = "VARCHAR(100) COMMENT '비밀번호(암호화 저장)'"
    )
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(
            length = 6,
            nullable = false,
            columnDefinition = "VARCHAR(6) COMMENT '회원 역할 (권한: ADMIN/USER 등)'"
    )
    private Role memberRole;

    @Column(
            length = 25,
            nullable = false,
            columnDefinition = "VARCHAR(25) COMMENT '닉네임'"
    )
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(
            length = 6,
            nullable = false,
            columnDefinition = "VARCHAR(6) COMMENT '로그인 타입(EMAIL, OAUTH 등)'"
    )
    private LoginType loginType;

    @Enumerated(EnumType.STRING)
    @Column(
            length = 10,
            nullable = false,
            columnDefinition = "VARCHAR(10) COMMENT '회원 상태(활성, 휴면, 탈퇴 등)'"
    )
    private TalkPickStatus status;

    @Enumerated(EnumType.STRING)
    @Column(
            columnDefinition = "VARCHAR(5) COMMENT 'MBTI 유형'"
    )
    private MBTI mbti;

    @Column(
            length = 255,
            columnDefinition = "VARCHAR(255) COMMENT 'OAuth Provider 식별값(구글, 카카오 등 연결용)'"
    )
    private String providerId;

    @Column(
            columnDefinition = "DATETIME COMMENT '회원 탈퇴 일시'"
    )
    private LocalDateTime deletedAt;

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateMbti(MBTI mbti) {this.mbti = mbti;}

    public void updateStatus(TalkPickStatus talkPickStatus) {this.status = talkPickStatus;}

    public void updatePassword(String password) {
        this.password = password;
    }

    public void withdraw() {
        this.status = TalkPickStatus.DIS_ACTIVE;
        this.deletedAt = LocalDateTime.now();
    }

    public void reactivate() {
        this.status = TalkPickStatus.ACTIVE;
        this.deletedAt = null;
    }

}
