package talkPick.domain.topic.domain;

import jakarta.persistence.*;
import lombok.*;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.domain.type.Gender;
import talkPick.domain.member.domain.type.MBTI;
import java.time.LocalDate;

//TODO 동시성 고려해야 함.
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

    @Version
    @Column(name = "version", nullable = false, columnDefinition = "BIGINT COMMENT '버전'")
    private Long version;

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

    public void addLike() {
        this.likeCount++;
    }

    //TODO 이 메서드 호출할 때 락 체크 + 리트라이 필요
    public void update(Member member, long talkTime) {
        MBTI mbti = MBTI.INFP;
        updateMBTI(mbti);
        updateAverageTalkTime(talkTime);
        this.selectCount++;
    }

    private void updateMBTI(MBTI mbti) {
        if (mbti != null) {
            String mbtiString = mbti.name();
            if (mbtiString.startsWith("E")) {
                this.eCount++;
            } else if (mbtiString.startsWith("I")) {
                this.iCount++;
            }
            if (mbtiString.charAt(1) == 'S') {
                this.sCount++;
            } else if (mbtiString.charAt(1) == 'N') {
                this.nCount++;
            }
            if (mbtiString.charAt(2) == 'F') {
                this.fCount++;
            } else if (mbtiString.charAt(2) == 'T') {
                this.tCount++;
            }
            if (mbtiString.charAt(3) == 'J') {
                this.jCount++;
            } else if (mbtiString.charAt(3) == 'P') {
                this.pCount++;
            }
        }
    }

    private void updateAverageTalkTime(long talkTime) {
        if (this.selectCount == 1) {
            this.averageTalkTime = talkTime;
        } else {
            this.averageTalkTime = ((this.averageTalkTime * (this.selectCount - 1)) + talkTime) / this.selectCount;
        }
    }
}