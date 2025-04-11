package talkPick.domain;

import jakarta.persistence.*;
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
    private String title;
    private String detail;
    private String thumbnail;
    private String icon;
    private long averageTalkTime;
    private long selectCount;
    private long likeCount;
    private long ECount;
    private long ICount;
    private long SCount;
    private long NCount;
    private long FCount;
    private long TCount;
    private long JCount;
    private long PCount;
    @Enumerated(EnumType.STRING)
    private TalkPickStatus status;

    public void addLike(Long likeCount) {
        this.likeCount = likeCount;
    }
}