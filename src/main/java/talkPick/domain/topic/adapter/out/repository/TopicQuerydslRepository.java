package talkPick.domain.topic.adapter.out.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import talkPick.domain.topic.adapter.in.dto.TopicReqDTO;
import talkPick.domain.topic.adapter.out.dto.TopicResDTO;
import talkPick.global.common.model.PageCustom;
import java.util.List;
import static talkPick.domain.topic.domain.QCategory.category;
import static talkPick.domain.topic.domain.QTopic.topic;
import static talkPick.domain.topic.domain.QTopicKeyword.topicKeyword;
import static talkPick.domain.topic.domain.QTopicStat.topicStat;

@Repository
public class TopicQuerydslRepository {
    private final JPAQueryFactory queryFactory;
    public TopicQuerydslRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public PageCustom<TopicResDTO.Categories> findCategoriesWithPageable(Pageable pageable) {
        Long totalElements = queryFactory
                .select(category.count())
                .from(category)
                .fetchOne();

        List<TopicResDTO.Categories> content = queryFactory.select(Projections.constructor(TopicResDTO.Categories.class,
                        category.id,
                        category.title,
                        category.description,
                        category.imageUrl,
                        category.categoryGroup
                ))
                .from(category)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        //TODO Count 쿼리 수 줄이기 위해 Redis 캐싱 적용해야 함.
        int totalPages = (int) Math.ceil((double) totalElements / pageable.getPageSize());
        return new PageCustom<>(content, totalPages, totalElements, pageable.getPageSize(), pageable.getPageNumber());
    }

    public List<TopicResDTO.TopicDetail> findTopicDetailsByIds(TopicReqDTO.TodayTopics requestDTO) {
        return queryFactory.select(Projections.constructor(TopicResDTO.TopicDetail.class,
                        topic.id,
                        topic.title,
                        topic.thumbnail,
                        topicStat.averageTalkTime,
                        topicStat.selectCount,
                        category.title,
                        category.categoryGroup,
                        topicKeyword.keyword
                ))
                .leftJoin(category).on(topic.categoryId.eq(category.id))
                .leftJoin(topicKeyword).on(topic.id.eq(topicKeyword.topicId))
                .leftJoin(topicStat).on(topic.id.eq(topicStat.topicId))
                .where(topic.id.in(requestDTO.topicIds()))
                .fetch();
    }

    public TopicResDTO.TopicDetail findTopicDetailById(Long topicId) {
        return queryFactory.select(Projections.constructor(TopicResDTO.TopicDetail.class,
                        topic.id,
                        topic.title,
                        topic.thumbnail,
                        topicStat.averageTalkTime,
                        topicStat.selectCount,
                        category.title,
                        category.categoryGroup,
                        topicKeyword.keyword
                ))
                .leftJoin(category).on(topic.categoryId.eq(category.id))
                .leftJoin(topicKeyword).on(topic.id.eq(topicKeyword.topicId))
                .leftJoin(topicStat).on(topic.id.eq(topicStat.topicId))
                .where(topic.id.eq(topicId))
                .fetchOne();
    }
}