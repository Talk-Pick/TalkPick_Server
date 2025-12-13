package talkPick.domain.random.domain;

import jakarta.persistence.*;
import lombok.*;
import talkPick.domain.random.adapter.in.dto.RandomReqDTO;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "random_topic_history")
public class RandomTopicHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT COMMENT '기본 키'")
    private Long id;

    @Column(name = "member_id", nullable = false, columnDefinition = "BIGINT COMMENT '회원 ID'")
    private Long memberId;

    @Column(name = "random_id", nullable = false, columnDefinition = "BIGINT COMMENT '랜덤 ID'")
    private Long randomId;

    @Column(name = "topic_id", nullable = false, columnDefinition = "BIGINT COMMENT 'Topic ID'")
    private Long topicId;

    @Column(name = "`order`", nullable = false, columnDefinition = "INT COMMENT '랜덤 코스 순서'")
    private Integer order;

    @Column(name = "start_at", nullable = false, columnDefinition = "DATETIME COMMENT '시작 시간'")
    private LocalDateTime startAt;

    @Column(name = "end_at", nullable = true, columnDefinition = "DATETIME COMMENT '종료 시간'")
    private LocalDateTime endAt;

    public static RandomTopicHistory of(final Long memberId, final Long randomId, RandomReqDTO.Record requestDTO) {
        return RandomTopicHistory.builder()
                .memberId(memberId)
                .randomId(randomId)
                .topicId(requestDTO.topicId())
                .order(requestDTO.order())
                .startAt(LocalDateTime.now())
                .endAt(null)
                .build();
    }

    public static RandomTopicHistory ofRecord(final Long memberId, final Long randomId, RandomReqDTO.TotalRecord requestDTO) {
        return RandomTopicHistory.builder()
                .memberId(memberId)
                .randomId(randomId)
                .topicId(requestDTO.topicId())
                .order(requestDTO.order())
                .startAt(requestDTO.startAt())
                .endAt(requestDTO.endAt())
                .build();
    }

    public void next() {
        this.endAt = LocalDateTime.now();
    }
}