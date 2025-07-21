package talkPick.domain.today.domain;

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
public class TodayTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    private Long topicId;

    public static TodayTopic of(Long memberId, Long topicId) {
        return TodayTopic.builder()
                .memberId(memberId)
                .topicId(topicId)
                .build();
    }
}