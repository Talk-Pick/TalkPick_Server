package talkPick.adapter.out.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import talkPick.adapter.in.dto.TopicReqDTO;
import talkPick.adapter.out.dto.TopicResDTO;
import talkPick.domain.type.Category;
import talkPick.model.PageCustom;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static talkPick.domain.QTopic.topic;
import static talkPick.domain.QTopicCategory.topicCategory;
import static talkPick.domain.QTopicKeyword.topicKeyword;

@Repository
public class TopicQuerydslRepository {
    private final JPAQueryFactory queryFactory;
    public TopicQuerydslRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Category> findTopCategories(int count) {
        return queryFactory
                .select(topicCategory.category)
                .from(topicCategory)
                .groupBy(topicCategory.category)
                .orderBy(topicCategory.category.count().desc())
                .limit(count)
                .fetch();
    }

    public PageCustom<TopicResDTO.Categories> findCategoriesWithPageable(Pageable pageable) {
        long totalElements = Optional.ofNullable(
                queryFactory.select(topicCategory.category.countDistinct())
                        .select(topicCategory.category.countDistinct())
                        .from(topicCategory)
                        .fetchOne()
        ).orElse(0L);

        List<TopicResDTO.Categories> content = queryFactory
                .select(topicCategory.category)
                .from(topicCategory)
                .groupBy(topicCategory.category)
                .orderBy(topicCategory.category.count().desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(category -> new TopicResDTO.Categories(category.name(), category.getDescription(), category.getImageUrl()))
                .collect(Collectors.toList());

        int totalPages = (int) Math.ceil((double) totalElements / pageable.getPageSize());
        return new PageCustom<>(content, totalPages, totalElements, pageable.getPageSize(), pageable.getPageNumber());
    }

    public List<TopicResDTO.TopicDetails> findTopicDetailsByIds(TopicReqDTO.TodayTopics requestDTO) {
        return queryFactory.select(Projections.constructor(TopicResDTO.TopicDetails.class,
                        topic.id,
                        topic.title,
                        topic.thumbnail,
                        topic.averageTalkTime,
                        topic.selectCount,
                        topicCategory.category,
                        topicKeyword.keyword
                ))
                .leftJoin(topicCategory).on(topic.id.eq(topicCategory.topicId))
                .leftJoin(topicKeyword).on(topic.id.eq(topicKeyword.topicId))
                .where(topic.id.in(requestDTO.topicIds()))
                .fetch();
    }
}