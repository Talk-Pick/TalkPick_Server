package talkPick.domain.auth.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import talkPick.core.common.model.BaseTime;
import talkPick.domain.member.domain.Member;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "refresh_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RefreshToken extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    public static RefreshToken of(final Member member, final String token, final LocalDateTime expiredAt) {
        return RefreshToken.builder()
                .member(member)
                .token(token)
                .expiredAt(expiredAt)
                .build();
    }

    public static RefreshToken fromDomain(final RefreshTokenInfo info, final Member member) {
        return RefreshToken.builder()
                .id(info.id())
                .member(member)
                .token(info.token())
                .expiredAt(info.expiredAt())
                .build();
    }

    public RefreshTokenInfo toDomain() {
        return RefreshTokenInfo.withId(id, member.getId(), token, expiredAt);
    }

    public void updateToken(String token, LocalDateTime expiredAt) {
        this.token = token;
        this.expiredAt = expiredAt;
    }
}