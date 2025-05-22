package talkPick.domain.topic.domain;

import jakarta.persistence.*;
import lombok.*;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.domain.profile.Profile;
import talkPick.domain.member.domain.type.Gender;
import talkPick.domain.member.domain.type.MBTI;

import java.time.LocalDate;

//TODO 동시성 고려해야 함.
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
    private Integer maleCount;
    private Integer femaleCount;

    @Version
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
                .teenCount(0)
                .twentiesCount(0)
                .thirtiesCount(0)
                .fiftiesCount(0)
                .maleCount(0)
                .femaleCount(0)
                .build();
    }

    public void addLike() {
        this.likeCount++;
    }

    //TODO 이 메서드 호출할 때 락 체크 + 리트라이 필요
    public void update(Member member, Profile profile, long talkTime) {
        MBTI mbti = MBTI.INFP;
        updateMBTI(mbti);
        updateAge(member.getBirth());
        updateGender(member.getGender());
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

    private void updateAge(LocalDate birth) {
        if (birth != null) {
            int age = LocalDate.now().getYear() - birth.getYear();

            if (age < 20) {
                this.teenCount++;
            } else if (age < 30) {
                this.twentiesCount++;
            } else if (age < 40) {
                this.thirtiesCount++;
            } else if (age < 50) {
                this.fortiesCount++;
            } else {
                this.fiftiesCount++;
            }
        }
    }

    private void updateGender(Gender gender) {
        if (gender != null) {
            if (gender == Gender.MALE) {
                this.maleCount++;
            } else if (gender == Gender.FEMALE) {
                this.femaleCount++;
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