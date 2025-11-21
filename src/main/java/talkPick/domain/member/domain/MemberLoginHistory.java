package talkPick.domain.member.domain;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberLoginHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            columnDefinition = "BIGINT COMMENT '로그인 기록 고유 PK'"
    )
    private Long id;

    @Column(
            name = "member_id",
            nullable = false,
            columnDefinition = "BIGINT COMMENT '회원 고유 PK (Foreign Key)'"
    )
    private Long memberId;

    @Column(
            columnDefinition = "DATETIME COMMENT '로그인 시각'"
    )
    private LocalDateTime loginTime;
}
