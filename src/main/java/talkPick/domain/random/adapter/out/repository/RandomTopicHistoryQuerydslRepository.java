package talkPick.domain.random.adapter.out.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import talkPick.domain.random.dto.RandomTopicHistoryDataDTO;
import java.util.List;
import static talkPick.domain.random.domain.QRandomTopicHistory.randomTopicHistory;

@Repository
@RequiredArgsConstructor
public class RandomTopicHistoryQuerydslRepository {
    private final JPAQueryFactory queryFactory;

    public List<RandomTopicHistoryDataDTO> getRandomTopicHistoriesByRandomId(Long randomId) {
        return queryFactory.select(Projections.constructor(RandomTopicHistoryDataDTO.class,
                        randomTopicHistory.topicId
                ))
                .from(randomTopicHistory)
                .where(randomTopicHistory.randomId.eq(randomId))
                .fetch();
    }
}
