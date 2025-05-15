package talkPick.domain.search.adapter.out.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import talkPick.domain.search.adapter.out.dto.SearchResDTO;
import talkPick.domain.topic.domain.QCategory;
import talkPick.global.common.model.PageCustom;
import java.util.List;
import static talkPick.domain.topic.domain.QTopic.topic;
import static talkPick.domain.topic.domain.QCategory.category;
import static talkPick.domain.topic.domain.QTopicKeyword.topicKeyword;
import static talkPick.domain.topic.domain.QTopicStat.topicStat;

@Repository
public class SearchQuerydslRepository {
    private final JPAQueryFactory queryFactory;
    public SearchQuerydslRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public PageCustom<SearchResDTO.Topic> findTopicsByCategoryWithPageable(String condition, Pageable pageable) {
        Long totalElements = queryFactory
                .select(topic.count())
                .where(category.title.eq(condition))
                .from(topic)
                .fetchOne();

        List<SearchResDTO.Topic> content = queryFactory.select(Projections.constructor(SearchResDTO.Topic.class,
                        topic.id,
                        topic.title,
                        category.title,
                        topicKeyword.keyword,
                        topicStat.selectCount,
                        topicStat.averageTalkTime
                ))
                .from(topic)
                .join(category).on(QCategory.category.id.eq(topic.categoryId))
                .join(topicKeyword).on(topicKeyword.topicId.eq(topic.id))
                .where(QCategory.category.title.eq(condition))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        //TODO Count 쿼리 수 줄이기 위해 Redis 캐싱 적용해야 함.
        int totalPages = (int) Math.ceil((double) totalElements / pageable.getPageSize());
        return new PageCustom<>(content, totalPages, totalElements, pageable.getPageSize(), pageable.getPageNumber());
    }
}