package talkPick.domain.today.adapter.out.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import talkPick.domain.today.adapter.out.dto.TodayTopicResDTO;
import java.util.List;
import static talkPick.domain.today.domain.QTodayTopic.todayTopic;
import static talkPick.domain.topic.domain.QCategory.category;
import static talkPick.domain.topic.domain.QKeyword.keyword;
import static talkPick.domain.topic.domain.QTopic.topic;
import static talkPick.domain.topic.domain.QTopicStat.topicStat;

@Repository
public class TodayTopicQuerydslRepository {
    private final JPAQueryFactory queryFactory;
    public TodayTopicQuerydslRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<TodayTopicResDTO.TopicSummaries> findTodayTopicSummaries(Long memberId) {
        return queryFactory.select(Projections.constructor(
                        TodayTopicResDTO.TopicSummaries.class,
                        topic.id,
                        topic.title,
                        topicStat.averageTalkTime,
                        topicStat.selectCount,
                        category.title,
                        keyword.name,
                        keyword.iconUrl
                ))
                .from(topic)
                .leftJoin(category).on(topic.categoryId.eq(category.id))
                .leftJoin(topicStat).on(topic.id.eq(topicStat.topicId))
                .leftJoin(keyword).on(topic.keywordId.eq(keyword.id))
                .where(topic.id.notIn(
                        JPAExpressions
                                .select(todayTopic.topicId)
                                .from(todayTopic)
                                .where(todayTopic.memberId.eq(memberId))
                ))
                .orderBy(Expressions.numberTemplate(Double.class, "rand()").asc())
                .limit(5)
                .fetch();
    }
}