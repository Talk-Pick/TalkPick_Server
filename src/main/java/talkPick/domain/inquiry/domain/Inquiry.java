package talkPick.domain.inquiry.domain;

import jakarta.persistence.*;
import lombok.*;
import talkPick.domain.inquiry.domain.type.InquiryType;
import talkPick.global.model.BaseTime;

@Entity
@Table(name = "inquiry")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inquiry extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_id", nullable = false, columnDefinition = "BIGINT COMMENT '기본 키'")
    private Long id;

    @Column(
            name = "member_id",
            nullable = false,
            columnDefinition = "BIGINT COMMENT '회원 PK (Foreign Key)'"
    )
    private Long memberId;

    @Column(name = "title", nullable = false, length = 200, columnDefinition = "VARCHAR(200) COMMENT '문의 제목'")
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT COMMENT '문의 본문 내용'")
    private String content;

    @Column(name = "email", nullable = false, length = 100, columnDefinition = "VARCHAR(100) COMMENT '답변 받을 이메일'")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20, columnDefinition = "VARCHAR(20) COMMENT '문의 상태'")
    private InquiryType type;

    @Column(name = "is_answered", nullable = false, columnDefinition = "TINYINT(1) COMMENT '답변 완료 여부'")
    private boolean isAnswered;
}
