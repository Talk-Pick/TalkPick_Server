package talkPick.domain.topic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import talkPick.domain.type.TopicType;
import talkPick.model.BaseTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberTopicHistory extends BaseTime {
    private Long id;
    private Long memberId;
    private Long topicId;
    private long talkTime;
    private boolean like;
    private int sequence;
    private TopicType topicType;
}
