package talkPick.domain.random.adapter.out.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import java.util.List;
import static talkPick.domain.random.domain.QRandomTopicHistory.randomTopicHistory;
import static talkPick.domain.topic.domain.QTopic.topic;

@Repository
public class SelectedRandomTopicQuerydslRepository {
    private final JPAQueryFactory queryFactory;
    public SelectedRandomTopicQuerydslRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    public RandomResDTO.Result findResultByRandomId(Long randomId) {
        List<RandomResDTO.ResultDetail> result = queryFactory.select(Projections.constructor(RandomResDTO.ResultDetail.class,
                        topic.id,
                        topic.title,
                        randomTopicHistory.category,
                        randomTopicHistory.keyword,
                        randomTopicHistory.startAt,
                        randomTopicHistory.endAt
                ))
                .join(topic).on(randomTopicHistory.topicId.eq(topic.id))
                .where(randomTopicHistory.randomId.eq(randomId))
                .from(randomTopicHistory)
                .fetch();
        return RandomResDTO.Result.of(randomId, result);
    }
}
