package talkPick.domain.like;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import talkPick.model.BaseTime;
import talkPick.model.TalkPickStatus;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberLikeHistory extends BaseTime {
    private Long id;
    private Long memberId;
    private Long topicId;
    private TalkPickStatus status;
}
