package talkPick.domain.search.adapter.out.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import talkPick.domain.search.adapter.out.dto.TopicSearchResDTO;
import talkPick.global.common.model.PageCustom;
import java.util.List;
import static talkPick.domain.search.domain.QTopicSearch.topicSearch;
import static talkPick.domain.topic.domain.QTopic.topic;
import static talkPick.domain.topic.domain.QCategory.category;
import static talkPick.domain.topic.domain.QTopicKeyword.topicKeyword;
import static talkPick.domain.topic.domain.QTopicStat.topicStat;

@Repository
public class TopicSearchQuerydslRepository {
    private final JPAQueryFactory queryFactory;
    public TopicSearchQuerydslRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public PageCustom<TopicSearchResDTO.Topic> findTopicsByWordWithPageable(String word, Pageable pageable) {
        List<TopicSearchResDTO.Topic> content = queryFactory
                .select(Projections.constructor(TopicSearchResDTO.Topic.class,
                        topic.id,
                        topic.title,
                        category.title,
                        topicKeyword.keyword,
                        topicStat.selectCount,
                        topicStat.averageTalkTime
                ))
                .from(topicSearch)
                .join(topic).on(topicSearch.topicId.eq(topic.id))
                .join(category).on(topic.categoryId.eq(category.id))
                .join(topicKeyword).on(topic.id.eq(topicKeyword.topicId))
                .join(topicStat).on(topic.id.eq(topicStat.topicId))
                .where(containsSearchText(word))
                .orderBy(
                        Expressions.numberTemplate(Double.class,
                                "MATCH(search_text) AGAINST({0} IN BOOLEAN MODE)",
                                word
                        ).desc()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalElements = queryFactory
                .select(topicSearch.count())
                .from(topicSearch)
                .where(containsSearchText(word))
                .fetchOne();

        int totalPages = (int) Math.ceil((double) totalElements / pageable.getPageSize());
        return new PageCustom<>(content, totalPages, totalElements, pageable.getPageSize(), pageable.getPageNumber());
    }

    private Predicate containsSearchText(String word) {
        if (word == null || word.isBlank()) {
            return null;
        }
        return topicSearch.searchText.containsIgnoreCase(word);
    }

}