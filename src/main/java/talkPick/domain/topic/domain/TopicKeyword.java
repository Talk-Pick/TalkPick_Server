package talkPick.domain.topic.domain;

import jakarta.persistence.*;
import lombok.*;
import talkPick.domain.topic.domain.type.Keyword;
import talkPick.global.model.BaseTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Topic 키워드 테이블")
public class TopicKeyword extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT COMMENT '기본 키'")
    private Long id;

    @Column(name = "topic_id", nullable = false, columnDefinition = "BIGINT COMMENT 'Topic ID'")
    private Long topicId;

    @Enumerated(EnumType.STRING)
    @Column(name = "keyword", nullable = false, length = 50, columnDefinition = "VARCHAR(50) COMMENT '키워드'")
    private Keyword keyword;

    public static TopicKeyword of(Long topicId, Keyword keyword) {
        return TopicKeyword.builder()
                .topicId(topicId)
                .keyword(keyword)
                .build();
    }
}