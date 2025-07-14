package talkPick.domain.random.adapter.out.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.random.dto.RandomTopicHistoryDataDTO;
import java.util.List;
import static talkPick.domain.random.domain.QRandomTopicHistory.randomTopicHistory;
import static talkPick.domain.topic.domain.QCategory.category;
import static talkPick.domain.topic.domain.QTopic.topic;
import static talkPick.domain.topic.domain.QTopicKeyword.topicKeyword;

@Repository
public class RandomTopicHistoryQuerydslRepository {
    private final JPAQueryFactory queryFactory;
    public RandomTopicHistoryQuerydslRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<RandomTopicHistoryDataDTO> getRandomTopicHistoriesByRandomId(Long randomId) {
        return queryFactory.select(Projections.constructor(RandomTopicHistoryDataDTO.class,
                        randomTopicHistory.topicId
                ))
                .from(randomTopicHistory)
                .where(randomTopicHistory.randomId.eq(randomId))
                .fetch();
    }

    public RandomResDTO.Result findResultByRandomId(Long randomId) {
        List<RandomResDTO.ResultDetail> result = queryFactory
                .select(Projections.constructor(RandomResDTO.ResultDetail.class,
                        topic.id,
                        topic.title,
                        category.title,
                        topicKeyword.keyword,
                        randomTopicHistory.startAt,
                        randomTopicHistory.endAt
                ))
                .from(randomTopicHistory)
                .join(topic).on(randomTopicHistory.topicId.eq(topic.id))
                .join(category).on(topic.categoryId.eq(category.id))
                .join(topicKeyword).on(randomTopicHistory.topicId.eq(topicKeyword.topicId))
                .where(randomTopicHistory.randomId.eq(randomId))
                .distinct()
                .fetch();

        return RandomResDTO.Result.of(randomId, result);
    }
}
