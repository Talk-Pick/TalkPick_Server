package talkPick.domain.random.adapter.out.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import talkPick.domain.random.dto.RandomTopicHistoryDataDTO;
import java.util.List;
import static talkPick.domain.random.domain.QRandomTopicHistory.randomTopicHistory;

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
}
