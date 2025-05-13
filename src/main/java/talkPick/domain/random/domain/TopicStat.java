package talkPick.domain.random.domain;

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

    public void addLike() {
        this.likeCount++;
    }
}
