package talkPick.infra.redis.model;

import jakarta.persistence.Id;
import org.springframework.data.redis.core.RedisHash;
import talkPick.domain.type.Category;
import talkPick.domain.type.Keyword;

import java.io.Serializable;

@RedisHash("topic_ranking")
public class TopicRanking implements Serializable {
    @Id
    private String id;
    private Long topicId;
    private String content;
    private Keyword keyword;
    private Category category;
    private String gender; //TODO Gender Enum으로 변환 작업해야 함. User 모듈에 있어서 그럼.
    private Age age;
    private int ECount;
    private int ICount;
    private int SCount;
    private int NCount;
    private int FCount;
    private int TCount;
    private int JCount;
    private int PCount;
    private boolean like;
    private long talkTime;
}
