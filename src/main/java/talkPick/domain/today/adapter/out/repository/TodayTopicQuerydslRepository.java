package talkPick.domain.today.adapter.out.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import talkPick.domain.today.adapter.out.dto.TodayTopicResDTO;
import java.time.LocalDate;
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
        var todayTopics = findTodayTopicsByDate(memberId);
        if (!todayTopics.isEmpty()) {
            return todayTopics;
        }

        return findRandomTopics();
    }

    public List<TodayTopicResDTO.TodayTopic> findTodayTopicsByDate(Long memberId) {
        var today = LocalDate.now();
        var startOfDay = today.atStartOfDay();
        var endOfDay = today.plusDays(1).atStartOfDay();

        return queryFactory.select(Projections.constructor(TodayTopicResDTO.TodayTopic.class,
                        topic.id,
                        topic.title,
                        category.title,
                        keyword.name,
                        keyword.iconUrl
                ))
                .from(todayTopic)
                .innerJoin(topic).on(todayTopic.topicId.eq(topic.id))
                .leftJoin(category).on(topic.categoryId.eq(category.id))
                .leftJoin(topicStat).on(topic.id.eq(topicStat.topicId))
                .leftJoin(keyword).on(topic.keywordId.eq(keyword.id))
                .where(todayTopic.memberId.eq(memberId)
                        .and(todayTopic.createdDate.goe(startOfDay))
                        .and(todayTopic.createdDate.lt(endOfDay)))
                .fetch();
    }

    private List<TodayTopicResDTO.TodayTopic> findRandomTopics() {
        var totalCount = getTotalTopicCount();
        if (totalCount <= LIMIT) {
            return findTopicsWithOffset(0);
        }

        var offset = new Random().nextLong(totalCount - LIMIT);
        return findTopicsWithOffset(offset);
    }

    private long getTotalTopicCount() {
        var count = queryFactory.select(topic.count())
                .from(topic)
                .fetchOne();
        return count != null ? count : 0;
    }

    private List<TodayTopicResDTO.TodayTopic> findTopicsWithOffset(long offset) {
        return queryFactory.select(Projections.constructor(TodayTopicResDTO.TodayTopic.class,
                        topic.id,
                        topic.title,
                        category.title,
                        keyword.name,
                        keyword.iconUrl
                ))
                .from(topic)
                .leftJoin(category).on(topic.categoryId.eq(category.id))
                .leftJoin(topicStat).on(topic.id.eq(topicStat.topicId))
                .leftJoin(keyword).on(topic.keywordId.eq(keyword.id))
                .offset(offset)
                .limit(LIMIT)
                .fetch();
    }
}