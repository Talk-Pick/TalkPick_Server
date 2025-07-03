package talkPick.domain.search.domain;

import jakarta.persistence.*;
import lombok.*;
import talkPick.global.model.BaseTime;

//TODO 추후 ElasticSearch로 넘어갈 테이블 (일단 보류)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TopicSearch extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long topicId;

    @Column(columnDefinition = "TEXT")
    private String searchText;
}