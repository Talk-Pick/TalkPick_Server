package talkPick.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import talkPick.domain.type.Keyword;
import talkPick.model.BaseTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopicKeyword extends BaseTime {
    private Long id;
    private Long topicId;
    private Keyword keyword;
}
