package talkPick.domain.member.domain.mapping;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import talkPick.global.model.BaseTime;


@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@DynamicInsert
@DynamicUpdate
public class MemberTerm extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "member_term_id",
            columnDefinition = "BIGINT COMMENT '회원-약관 매핑의 기본 키'"
    )
    private Long id;

    @Column(
            name = "member_id",
            nullable = false,
            columnDefinition = "BIGINT COMMENT '회원 PK (Foreign Key)'"
    )
    private Long memberId;

    @Column(
            name = "term_id",
            nullable = false,
            columnDefinition = "BIGINT COMMENT '약관 PK (Foreign Key)'"
    )
    private Long termId;

    @Column(
            nullable = false,
            columnDefinition = "TINYINT(1) DEFAULT 0 COMMENT '약관 동의 여부'"
    )
    @ColumnDefault("false")
    private Boolean isAgree;

    public void updateIsAgree(boolean isAgree) {
        this.isAgree = isAgree;
    }
}

