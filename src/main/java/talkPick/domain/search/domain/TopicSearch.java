package talkPick.domain.search.domain;

import jakarta.persistence.*;
import lombok.*;
import talkPick.domain.topic.domain.Category;
import talkPick.domain.topic.domain.Topic;
import talkPick.domain.topic.domain.type.Keyword;
import talkPick.global.common.model.BaseTime;

//TODO 추후 ELK로 넘어갈 테이블
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Table(name = "topic_search", indexes = {
//        @Index(name = "idx_topic_search_text", columnList = "search_text", unique = false)
//})
public class TopicSearch extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long topicId;

    @Column(columnDefinition = "TEXT")
    private String searchText;

    public void updateSearchText(Topic topic, Category category, Keyword keyword) {
        String normalizedTitle = normalizeText(topic.getTitle());
        String normalizedCategory = normalizeText(category.getTitle());
        String normalizedKeyword = normalizeText(keyword.toString());

        this.searchText = String.format("%s %s %s %s %s %s %s",
                normalizedTitle, normalizedTitle, normalizedTitle,
                normalizedCategory, normalizedCategory,
                normalizedKeyword, normalizedKeyword
        );
    }

    private String normalizeText(String text) {
        if (text == null || text.isBlank()) {
            return "";
        }
        return text.trim()
                .replaceAll("\\s+", "")
                .toLowerCase();
    }
}