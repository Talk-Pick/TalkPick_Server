package talkPick.domain.search.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * 해당 코드 사용 안 함.
 * **/
@Deprecated
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "검색 기록 테이블")
public class TopicSearchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT COMMENT '기본 키'")
    private Long id;

    @Column(name = "member_id", nullable = false, columnDefinition = "BIGINT COMMENT '회원 ID'")
    private Long memberId;

    @Column(name = "word", nullable = false, length = 255, columnDefinition = "VARCHAR(255) COMMENT '검색 키워드'")
    private String word;

    @Column(name = "search_at", nullable = false, columnDefinition = "DATETIME COMMENT '검색 시각'")
    private LocalDateTime searchAt;

    @Column(name = "is_result_shown", nullable = false, columnDefinition = "TINYINT(1) COMMENT '검색 결과 표시 여부'")
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