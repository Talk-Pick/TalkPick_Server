package talkPick.adapter.out.topic.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import talkPick.adapter.in.topic.dto.TopicReqDTO;
import talkPick.adapter.out.topic.dto.TopicResDTO;
import talkPick.common.model.PageCustom;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static talkPick.domain.topic.QTopic.topic;
import static talkPick.domain.topic.QTopicCategory.topicCategory;
import static talkPick.domain.topic.QTopicKeyword.topicKeyword;

@Repository
public class TopicQuerydslRepository {
    private final JPAQueryFactory queryFactory;
    public TopicQuerydslRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
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

        //TODO Count 쿼리 수 줄이기 위해 Redis 캐싱 적용해야 함.
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