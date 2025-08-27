package talkPick.domain.topic.domain.member;


import jakarta.persistence.*;
import lombok.*;
import talkPick.domain.member.domain.Member;

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

    @OneToOne(mappedBy = "memberTopicResult", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    private MemberTopicHistory memberTopicHistory;

    @Column(
            name = "member_id",
            nullable = false,
            columnDefinition = "BIGINT COMMENT '회원 PK (Foreign Key)'"
    )
    private Long memberId;


}
