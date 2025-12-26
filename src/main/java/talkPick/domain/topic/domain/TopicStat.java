package talkPick.domain.topic.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "topic_stat")
public class TopicStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT COMMENT '기본 키'")
    private Long id;

    @Column(name = "topic_id", nullable = false, columnDefinition = "BIGINT COMMENT 'Topic ID'")
    private Long topicId;

    @Column(name = "e_count", nullable = false, columnDefinition = "INT COMMENT 'MBTI E 수'")
    private Integer eCount;

    @Column(name = "i_count", nullable = false, columnDefinition = "INT COMMENT 'MBTI I 수'")
    private Integer iCount;

    @Column(name = "s_count", nullable = false, columnDefinition = "INT COMMENT 'MBTI S 수'")
    private Integer sCount;

    @Column(name = "n_count", nullable = false, columnDefinition = "INT COMMENT 'MBTI N 수'")
    private Integer nCount;

    @Column(name = "f_count", nullable = false, columnDefinition = "INT COMMENT 'MBTI F 수'")
    private Integer fCount;

    @Column(name = "t_count", nullable = false, columnDefinition = "INT COMMENT 'MBTI T 수'")
    private Integer tCount;

    @Column(name = "j_count", nullable = false, columnDefinition = "INT COMMENT 'MBTI J 수'")
    private Integer jCount;

    @Column(name = "p_count", nullable = false, columnDefinition = "INT COMMENT 'MBTI P 수'")
    private Integer pCount;

    @Column(name = "like_count", nullable = false, columnDefinition = "INT COMMENT '좋아요 수'")
    private Integer likeCount;

    @Column(name = "teen_count", nullable = false, columnDefinition = "INT COMMENT '10대 수'")
    private Integer teenCount;

    @Column(name = "twenties_count", nullable = false, columnDefinition = "INT COMMENT '20대 수'")
    private Integer twentiesCount;

    @Column(name = "thirties_count", nullable = false, columnDefinition = "INT COMMENT '30대 수'")
    private Integer thirtiesCount;

    @Column(name = "forties_count", nullable = false, columnDefinition = "INT COMMENT '40대 수'")
    private Integer fortiesCount;

    @Column(name = "fifties_count", nullable = false, columnDefinition = "INT COMMENT '50대 수'")
    private Integer fiftiesCount;

    @Column(name = "male_count", nullable = false, columnDefinition = "INT COMMENT '남성 수'")
    private Integer maleCount;

    @Column(name = "female_count", nullable = false, columnDefinition = "INT COMMENT '여성 수'")
    private Integer femaleCount;

    @Column(name = "select_count", nullable = false, columnDefinition = "INT COMMENT '선택 횟수'")
    private Integer selectCount;

    @Column(name = "average_talk_time", nullable = false, columnDefinition = "BIGINT COMMENT '평균 토크 시간(ms)'")
    private long averageTalkTime;

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
                .build();
    }
}