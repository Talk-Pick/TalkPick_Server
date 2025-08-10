package talkPick.domain.token.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import talkPick.domain.member.domain.Member;
import talkPick.global.model.BaseTime;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@DynamicInsert
@DynamicUpdate
public class MemberToken extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_token_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 500, nullable = false, updatable = false)
    private String refreshToken;

    @Column(nullable = false)
    private LocalDateTime expireAt;

    public void updateRefreshTokenAndExpireAt (String refreshToken, LocalDateTime expireAt) {
        this.refreshToken = refreshToken;
        this.expireAt = expireAt;
    }
}

