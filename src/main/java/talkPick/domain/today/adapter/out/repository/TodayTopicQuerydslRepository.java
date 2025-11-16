package talkPick.domain.today.adapter.out.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import talkPick.domain.today.adapter.out.dto.TodayTopicResDTO;
import java.util.List;
import java.util.Random;
import static talkPick.domain.today.domain.QTodayTopic.todayTopic;
import static talkPick.domain.topic.domain.QCategory.category;
import static talkPick.domain.topic.domain.QKeyword.keyword;
import static talkPick.domain.topic.domain.QTopic.topic;
import static talkPick.domain.topic.domain.QTopicStat.topicStat;

@Repository
@RequiredArgsConstructor
public class TodayTopicQuerydslRepository {
    private final JPAQueryFactory queryFactory;
    private static final int LIMIT = 5;

    public List<TodayTopicResDTO.TodayTopic> findTodayTopics(Long memberId) {
        long totalCount = getTotalCount(memberId);
        if (totalCount <= LIMIT) {
            return findByConditions(memberId, 0, Long.MAX_VALUE);
        }

        long offset = new Random().nextLong(totalCount - LIMIT);
        return findByConditions(memberId, offset, LIMIT);
    }

    private long getTotalCount(Long memberId) {
        var count = queryFactory.select(topic.count())
                .from(topic)
                .where(topic.id.notIn(
                        JPAExpressions
                                .select(todayTopic.topicId)
                                .from(todayTopic)
                                .where(todayTopic.memberId.eq(memberId))
                ))
                .fetchOne();
        return count != null ? count : 0;
    }

    private List<TodayTopicResDTO.TodayTopic> findByConditions(Long memberId, long offset, long limit) {
        return queryFactory.select(Projections.constructor(
                        TodayTopicResDTO.TodayTopic.class,
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
                .offset(offset)
                .limit(limit)
                .fetch();
    }
}