package talkPick.domain.today.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "오늘의 Topic 테이블")
public class TodayTopic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT COMMENT '기본 키'")
    private Long id;

    @Column(name = "member_id", nullable = false, columnDefinition = "BIGINT COMMENT '회원 ID'")
    private Long memberId;

    @Column(name = "topic_id", nullable = false, columnDefinition = "BIGINT COMMENT 'Topic ID'")
    private Long topicId;

    public static TodayTopic of(Long memberId, Long topicId) {
        return TodayTopic.builder()
                .memberId(memberId)
                .topicId(topicId)
                .build();
    }
}