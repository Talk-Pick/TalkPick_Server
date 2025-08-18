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

    @OneToOne
    @JoinColumn(name = "member_topic_history_id")
    private MemberTopicHistory memberTopicHistory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


}
