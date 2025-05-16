package talkPick.domain.topic.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

//TODO 동시성 고려해야 함.
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TopicStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //TODO 토픽 ID가 갈아치울 예정
    private Long topicId;
    private Integer eCount;
    private Integer iCount;
    private Integer sCount;
    private Integer nCount;
    private Integer fCount;
    private Integer tCount;
    private Integer jCount;
    private Integer pCount;
    private long averageTalkTime;
    private Integer selectCount;
    private Integer likeCount;
    private Integer teenCount;
    private Integer twentiesCount;
    private Integer thirtiesCount;
    private Integer fortiesCount;
    private Integer fiftiesCount;

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