package talkPick.adaptor.out.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import talkPick.adaptor.out.dto.TopicResDTO;
import talkPick.domain.QTopicCategory;
import talkPick.domain.TopicCategory;
import talkPick.domain.type.Category;
import talkPick.model.PageCustom;
import talkPick.model.TalkPickStatus;

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

    public List<TopicResDTO.Topics> findTopLikedTopics(int count) {
        return queryFactory.select(Projections.constructor(TopicResDTO.Topics.class,
                topic.id,
                topic.content,
                topic.averageTalkTime,
                topic.selectCount,
                topicCategory.category,
                topicKeyword.keyword
                ))
                .from(topic)
                .leftJoin(topicCategory).on(topicCategory.topicId.eq(topic.id))
                .leftJoin(topicKeyword).on(topicKeyword.topicId.eq(topic.id))
                .orderBy(topic.likeCount.count().desc())
                .limit(count)
                .where(
                        topic.status.eq(TalkPickStatus.ACTIVE)
                )
                .fetch();
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
                .map(category -> new TopicResDTO.Categories(category.name(), category.getDescription()))
                .collect(Collectors.toList());

        int totalPages = (int) Math.ceil((double) totalElements / pageable.getPageSize());
        return new PageCustom<>(content, totalPages, totalElements, pageable.getPageSize(), pageable.getPageNumber());
    }
}