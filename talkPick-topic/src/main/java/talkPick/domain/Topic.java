package talkPick.domain;

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
public class Topic extends BaseTime {
    private Long id;
    private String content;
    private long averageTalkTime;
    private int selectCount;
    private int likeCount;
    private TalkPickStatus status;
}