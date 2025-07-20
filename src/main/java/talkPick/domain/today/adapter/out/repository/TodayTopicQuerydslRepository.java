package talkPick.domain.today.adapter.out.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import talkPick.domain.today.adapter.in.dto.TodayTopicReqDTO;
import talkPick.domain.today.adapter.out.dto.TodayTopicResDTO;
import java.util.List;
import static talkPick.domain.topic.domain.QCategory.category;
import static talkPick.domain.topic.domain.QTopic.topic;
import static talkPick.domain.topic.domain.QTopicKeyword.topicKeyword;
import static talkPick.domain.topic.domain.QTopicStat.topicStat;

@Repository
public class TodayTopicQuerydslRepository {
    private final JPAQueryFactory queryFactory;
    public TodayTopicQuerydslRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<TodayTopicResDTO.TopicDetail> findTopicDetailsByIds(TodayTopicReqDTO.TodayTopics requestDTO) {
        return queryFactory.select(Projections.constructor(TodayTopicResDTO.TopicDetail.class,
                        topic.id,
                        topic.title,
                        topic.thumbnail,
                        topicStat.averageTalkTime,
                        topicStat.selectCount,
                        category.title,
                        category.categoryGroup,
                        topicKeyword.keyword
                ))
                .from(topic)
                .leftJoin(category).on(topic.categoryId.eq(category.id))
                .leftJoin(topicKeyword).on(topic.id.eq(topicKeyword.topicId))
                .leftJoin(topicStat).on(topic.id.eq(topicStat.topicId))
                .where(topic.id.in(requestDTO.topicIds()))
                .fetch();
    }

    public List<TodayTopicResDTO.TopicSummaries> findTodayTopicSummaries(Long memberId) {
        return null;
    }
}
