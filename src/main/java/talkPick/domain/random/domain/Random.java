package talkPick.domain.random.domain;

import jakarta.persistence.*;
import lombok.*;
import talkPick.domain.random.adapter.in.dto.RandomReqDTO;
import talkPick.domain.random.domain.type.RandomType;
import talkPick.core.common.model.BaseTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "random")
public class Random extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT COMMENT '기본 키'")
    private Long id;

    @Column(name = "member_id", nullable = false, columnDefinition = "BIGINT COMMENT '회원 ID'")
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, columnDefinition = "VARCHAR(20) COMMENT '랜덤 진행 상태'")
    private RandomType type;

    @Column(name = "one_line", nullable = true, length = 255, columnDefinition = "VARCHAR(255) COMMENT '한 줄 평'")
    private String oneLine;

    @Column(name = "rating", nullable = true, columnDefinition = "INT COMMENT '평점'")
    private Integer rating;

    public static Random from(final Long memberId) {
        return Random.builder()
                .memberId(memberId)
                .type(RandomType.START)
                .oneLine(null)
                .rating(null)
                .build();
    }

    public void quit() {
        this.type = RandomType.QUIT;
    }

    public void end() {
        this.type = RandomType.COMPLETED;
    }

    public void rate(RandomReqDTO.Rate requestDTO) {
        this.rating = requestDTO.rating();
    }

    public void comment(RandomReqDTO.Comment requestDTO) {
        this.oneLine = requestDTO.oneLine();
    }
}