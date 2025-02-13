package talkPick.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import talkPick.domain.type.Category;
import talkPick.model.BaseTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopicCategory extends BaseTime {
    private Long id;
    private Long topicId;
    private Category category;
}
