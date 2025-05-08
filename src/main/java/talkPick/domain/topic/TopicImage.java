package talkPick.domain.topic;

import jakarta.persistence.*;
import lombok.*;
import talkPick.common.model.TalkPickStatus;

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
}