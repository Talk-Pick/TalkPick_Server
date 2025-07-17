package talkPick.domain.topic.domain;

import jakarta.persistence.*;
import lombok.*;
import talkPick.global.model.TalkPickStatus;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TopicImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long topicId;
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private TalkPickStatus status;

    public static TopicImage of(Long topicId, String imageUrl) {
        return TopicImage.builder()
                .topicId(topicId)
                .imageUrl(imageUrl)
                .status(TalkPickStatus.ACTIVE)
                .build();
    }
}