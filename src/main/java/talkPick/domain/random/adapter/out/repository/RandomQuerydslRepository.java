package talkPick.domain.random.adapter.out.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.core.common.model.TalkPickStatus;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static talkPick.domain.random.domain.QRandomTopicHistory.randomTopicHistory;
import static talkPick.domain.topic.domain.QCategory.category;
import static talkPick.domain.topic.domain.QKeyword.keyword;
import static talkPick.domain.topic.domain.QTopic.topic;

@Repository
@RequiredArgsConstructor
public class RandomQuerydslRepository {
    private final JPAQueryFactory queryFactory;

    public List<RandomResDTO.RandomTopicDetail> findRandomTopics(Long memberId, Long randomId, String categoryType){
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

        if (categoryType != null) {
            builder.and(category.title.eq(categoryType));
        }

        List<RandomResDTO.RandomTopicDetail> topics = queryFactory
                .select(Projections.constructor(RandomResDTO.RandomTopicDetail.class,
                        topic.id,
                        topic.title,
                        topic.detail,
                        category.title,
                        keyword.name,
                        keyword.imageUrl,
                        keyword.iconUrl
                ))
                .from(topic)
                .leftJoin(category).on(topic.categoryId.eq(category.id))
                .leftJoin(keyword).on(topic.keywordId.eq(keyword.id))
                .where(builder)
                .limit(20)
                .fetch();

        List<RandomResDTO.RandomTopicDetail> shuffledTopics = new ArrayList<>(topics);
        Collections.shuffle(shuffledTopics);
        return shuffledTopics.stream()
                .limit(4)
                .toList();
    }
}