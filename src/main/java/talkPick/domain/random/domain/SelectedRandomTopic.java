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
public class SelectedRandomTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    private Long randomId;
    private Long topicId;
    @Enumerated(EnumType.STRING)
    private CategoryGroup category;
    @Enumerated(EnumType.STRING)
    private Keyword keyword;
    private Integer order;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    public static SelectedRandomTopic ofByCategory(final Long memberId, RandomReqDTO.SelectCategory requestDTO) {
        return SelectedRandomTopic.builder()
                .memberId(memberId)
                .randomId(requestDTO.randomId())
                .topicId(null)
                .category(CategoryGroup.valueOf(requestDTO.category()))
                .keyword(null)
                .order(0)
                .build();
    }

    public static SelectedRandomTopic ofByTopic(final Long memberId, RandomReqDTO.SelectTopic requestDTO) {
        return SelectedRandomTopic.builder()
                .memberId(memberId)
                .randomId(requestDTO.randomId())
                .topicId(requestDTO.topicId())
                .category(CategoryGroup.valueOf(requestDTO.category()))
                .keyword(Keyword.valueOf(requestDTO.keyword()))
                .order(requestDTO.order())
                .build();
    }
}
