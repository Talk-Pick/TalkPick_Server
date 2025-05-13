package talkPick.domain.random.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import talkPick.domain.random.domain.type.RandomType;
import talkPick.global.common.model.BaseTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Random extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    private RandomType type;

    public static Random from(final Long memberId) {
        return Random.builder()
                .memberId(memberId)
                .type(RandomType.NOT_STARTED)
                .build();
    }

    public void start() {
        this.type = RandomType.IN_PROGRESS;
    }

    public void quit() {
        this.type = RandomType.QUIT;
    }
}