package talkPick.domain.topic.domain.member;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberTopicResult {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_topic_result_id")
    Long id;

    @Column(length = 100)
    String comment;

    @Column(name = "member_topic_history_id",
            nullable = false,
            columnDefinition = "BIGINT COMMENT 'MemberTopicHistory ID'")
    private Long memberTopicHistoryId;

    @Column(
            name = "member_id",
            nullable = false,
            columnDefinition = "BIGINT COMMENT '회원 PK (Foreign Key)'"
    )
    private Long memberId;

    /**
     * 코멘트 업데이트
     */
    public void updateComment(String comment) {
        this.comment = comment;
    }

    /**
     * member_topic_history_id getter
     */
    public Long getMemberTopicHistoryId() {
        return memberTopicHistoryId;
    }
}
