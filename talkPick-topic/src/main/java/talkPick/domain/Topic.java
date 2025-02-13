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
    private int ECount;
    private int ICount;
    private int SCount;
    private int NCount;
    private int FCount;
    private int TCount;
    private int JCount;
    private int PCount;
    private TalkPickStatus status;
}