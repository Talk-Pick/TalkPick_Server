package talkPick.domain.search.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TopicSearchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    private String word;
    private LocalDateTime searchAt;
    private Boolean isResultShown;

    public static TopicSearchHistory of(Long memberId, String word, Boolean isResultShown) {
        return TopicSearchHistory.builder()
                .memberId(memberId)
                .word(word)
                .searchAt(LocalDateTime.now())
                .isResultShown(isResultShown)
                .build();
    }
}