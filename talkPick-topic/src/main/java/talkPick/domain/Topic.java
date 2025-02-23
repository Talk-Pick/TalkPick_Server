package talkPick.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import talkPick.model.BaseTime;
import talkPick.model.TalkPickStatus;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Topic extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String detail;
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