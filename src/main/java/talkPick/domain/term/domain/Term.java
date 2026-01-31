package talkPick.domain.term.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import talkPick.core.common.model.BaseTime;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@DynamicInsert
@DynamicUpdate
public class Term extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "term_id",
            columnDefinition = "BIGINT COMMENT '약관 고유 PK'"
    )
    private Long id;

    @Column(
            length = 30,
            nullable = false,
            columnDefinition = "VARCHAR(30) COMMENT '약관 구분(예: 서비스, 개인정보 등)'"
    )
    private String termSort;

    @Column(
            nullable = false,
            columnDefinition = "TINYINT(1) DEFAULT 0 COMMENT '필수 동의 여부 (true: 필수, false: 선택)'"
    )
    @ColumnDefault("false")
    private Boolean isRequired;
}