package talkPick.domain.random.adapter.out.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.global.common.model.TalkPickStatus;

import java.util.List;
import java.util.stream.Collectors;

import static talkPick.domain.topic.domain.QTopic.topic;
import static talkPick.domain.topic.domain.QTopicCategory.topicCategory;
import static talkPick.domain.topic.domain.QTopicImage.topicImage;
import static talkPick.domain.topic.domain.QTopicKeyword.topicKeyword;

@Repository
public class RandomQuerydslRepository {
    private final JPAQueryFactory queryFactory;
    public RandomQuerydslRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<RandomResDTO.Categories> findCategories() {
        return queryFactory
                .select(topicCategory.category)
                .from(topicCategory)
                .groupBy(topicCategory.category)
                .orderBy(topicCategory.category.count().desc())
                .fetch()
                .stream()
                .map(category -> new RandomResDTO.Categories(category.name(), category.getImageUrl()))
                .collect(Collectors.toList());
    }

    public RandomResDTO.RandomTopicDetail findRandomTopicDetail(Long topicId) {
        return queryFactory.select(Projections.constructor(RandomResDTO.RandomTopicDetail.class,
                        topic.id,
                        topic.title,
                        topic.detail,
                        topic.thumbnail,
                        topic.icon,
                        topicCategory.category,
                        topicKeyword.keyword
                ))
                .leftJoin(topicCategory).on(topic.id.eq(topicCategory.topicId))
                .leftJoin(topicKeyword).on(topic.id.eq(topicKeyword.topicId))
                .where(topic.id.eq(topicId))
                .fetchOne();
    }

    public List<String> findRandomTopicImages(Long topicId) {
        return queryFactory.select(topicImage.imageUrl)
                .from(topicImage)
                .where(topicImage.topicId.eq(topicId),
                        topicImage.status.eq(TalkPickStatus.ACTIVE))
                .fetch();
    }
}