package talkPick.domain.topic.domain;

import jakarta.persistence.*;
import lombok.*;
import talkPick.global.model.TalkPickStatus;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Topic 이미지 테이블")
public class TopicImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT COMMENT '기본 키'")
    private Long id;

    @Column(name = "topic_id", nullable = false, columnDefinition = "BIGINT COMMENT 'Topic ID'")
    private Long topicId;

    @Column(name = "image_url", nullable = false, length = 500, columnDefinition = "VARCHAR(500) COMMENT '이미지 URL'")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "VARCHAR(20) COMMENT '이미지 상태'")
    private TalkPickStatus status;

    public static TopicImage of(Long topicId, String imageUrl) {
        return TopicImage.builder()
                .topicId(topicId)
                .imageUrl(imageUrl)
                .status(TalkPickStatus.ACTIVE)
                .build();
    }
}