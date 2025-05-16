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
    private long eCount;
    private long iCount;
    private long sCount;
    private long nCount;
    private long fCount;
    private long tCount;
    private long jCount;
    private long pCount;
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
                .eCount(0)
                .iCount(0)
                .sCount(0)
                .nCount(0)
                .fCount(0)
                .tCount(0)
                .jCount(0)
                .pCount(0)
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