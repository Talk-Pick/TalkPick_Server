package talkPick.domain.random.domain;

import jakarta.persistence.*;
import lombok.*;
import talkPick.domain.random.adapter.in.dto.RandomReqDTO;
import talkPick.domain.topic.domain.type.Category;
import talkPick.domain.topic.domain.type.Keyword;

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
    private Category category;
    @Enumerated(EnumType.STRING)
    private Keyword keyword;
    private Integer order;

    public static SelectedRandomTopic ofByCategory(final Long memberId, RandomReqDTO.SelectCategory requestDTO) {
        return SelectedRandomTopic.builder()
                .memberId(memberId)
                .randomId(requestDTO.randomId())
                .topicId(null)
                .category(Category.valueOf(requestDTO.category()))
                .keyword(null)
                .order(0)
                .build();
    }

    public static SelectedRandomTopic ofByTopic(final Long memberId, RandomReqDTO.SelectTopic requestDTO) {
        return SelectedRandomTopic.builder()
                .memberId(memberId)
                .randomId(requestDTO.randomId())
                .topicId(requestDTO.topicId())
                .category(Category.valueOf(requestDTO.category()))
                .keyword(Keyword.valueOf(requestDTO.keyword()))
                .order(requestDTO.order())
                .build();
    }
}
