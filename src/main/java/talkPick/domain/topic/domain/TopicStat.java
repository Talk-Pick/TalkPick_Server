package talkPick.domain.topic.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TopicStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long topicId;
    private long ECount;
    private long ICount;
    private long SCount;
    private long NCount;
    private long FCount;
    private long TCount;
    private long JCount;
    private long PCount;
    private long averageTalkTime;
    private long selectCount;
    private long likeCount;
    private long teenCount;
    private long twentiesCount;
    private long thirtiesCount;
    private long fortiesCount;
    private long fiftiesCount;

    public static TopicStat of(Long topicId) {
        return TopicStat.builder()
                .topicId(topicId)
                .ECount(0)
                .ICount(0)
                .SCount(0)
                .NCount(0)
                .FCount(0)
                .TCount(0)
                .JCount(0)
                .PCount(0)
                .averageTalkTime(0)
                .selectCount(0)
                .likeCount(0)
                .teenCount(0)
                .twentiesCount(0)
                .thirtiesCount(0)
                .fortiesCount(0)
                .fiftiesCount(0)
                .build();
    }

    public void addLike() {
        this.likeCount++;
    }
}