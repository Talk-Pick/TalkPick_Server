package talkPick.domain.random.domain;

import jakarta.persistence.*;
import lombok.*;
import talkPick.domain.random.adapter.in.dto.RandomReqDTO;
import talkPick.domain.topic.domain.type.CategoryGroup;
import talkPick.domain.topic.domain.type.Keyword;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RandomTopicHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    private Long randomId;
    private Long topicId;

    @Column(name = "`order`")
    private Integer order;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    public static RandomTopicHistory ofByCategory(final Long memberId, RandomReqDTO.SelectCategory requestDTO) {
        return RandomTopicHistory.builder()
                .memberId(memberId)
                .randomId(requestDTO.randomId())
                .topicId(null)
                .order(0)
                .startAt(LocalDateTime.now())
                .endAt(null)
                .build();
    }

    public static RandomTopicHistory ofByTopic(final Long memberId, RandomReqDTO.SelectTopic requestDTO) {
        return RandomTopicHistory.builder()
                .memberId(memberId)
                .randomId(requestDTO.randomId())
                .topicId(requestDTO.topicId())
                .order(requestDTO.order())
                .startAt(LocalDateTime.now())
                .endAt(null)
                .build();
    }
}
