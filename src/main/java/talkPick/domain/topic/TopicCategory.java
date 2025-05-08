package talkPick.domain.topic;

import jakarta.persistence.*;
import lombok.*;
import talkPick.domain.topic.type.Category;
import talkPick.common.model.BaseTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TopicCategory extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long topicId;
    @Enumerated(EnumType.STRING)
    private Category category;
}