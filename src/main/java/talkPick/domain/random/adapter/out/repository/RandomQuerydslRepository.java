package talkPick.domain.random.adapter.out.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.topic.domain.type.CategoryGroup;
import talkPick.global.model.TalkPickStatus;
import java.util.List;
import static talkPick.domain.random.domain.QRandomTopicHistory.randomTopicHistory;
import static talkPick.domain.topic.domain.QCategory.category;
import static talkPick.domain.topic.domain.QTopic.topic;
import static talkPick.domain.topic.domain.QTopicKeyword.topicKeyword;

@Repository
public class RandomQuerydslRepository {
    private final JPAQueryFactory queryFactory;
    public RandomQuerydslRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<RandomResDTO.RandomTopicDetail> findRandomTopics(Long memberId, Long randomId, CategoryGroup categoryGroup, String categoryType){
        List<Long> alreadyUsedTopicIds = queryFactory
                .select(randomTopicHistory.topicId)
                .from(randomTopicHistory)
                .where(randomTopicHistory.memberId.eq(memberId)
                        .and(randomTopicHistory.randomId.eq(randomId)))
                .fetch();

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(topic.status.eq(TalkPickStatus.ACTIVE));

        if (!alreadyUsedTopicIds.isEmpty()) {
            builder.and(topic.id.notIn(alreadyUsedTopicIds));
        }

        if (categoryGroup != null) {
            builder.and(category.categoryGroup.eq(categoryGroup));
        }

        if (categoryType != null) {
            builder.and(category.title.eq(categoryType));
        }

        return queryFactory
                .select(Projections.constructor(RandomResDTO.RandomTopicDetail.class,
                        topic.id,
                        topic.title,
                        topic.detail,
                        category.categoryGroup.stringValue(),
                        category.title,
                        topicKeyword.keyword
                ))
                .from(topic)
                .leftJoin(category).on(topic.categoryId.eq(category.id))
                .leftJoin(topicKeyword).on(topic.id.eq(topicKeyword.topicId))
                .where(builder)
                .fetch();
    }
}