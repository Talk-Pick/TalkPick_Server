package talkPick.domain.random.adapter.out.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.global.common.model.TalkPickStatus;
import java.util.List;

import static talkPick.domain.topic.domain.QCategory.category;
import static talkPick.domain.topic.domain.QTopic.topic;
import static talkPick.domain.topic.domain.QTopicImage.topicImage;
import static talkPick.domain.topic.domain.QTopicKeyword.topicKeyword;

@Repository
public class RandomQuerydslRepository {
    private final JPAQueryFactory queryFactory;
    public RandomQuerydslRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<RandomResDTO.Categories> findCategories() {
        return queryFactory.select(Projections.constructor(RandomResDTO.Categories.class,
                        category.id,
                        category.title,
                        category.imageUrl
                ))
                .from(category)
                .fetch();
    }

    public RandomResDTO.RandomTopicDetail findRandomTopicDetail(Long topicId) {
        return queryFactory.select(Projections.constructor(RandomResDTO.RandomTopicDetail.class,
                        topic.id,
                        topic.title,
                        topic.detail,
                        topic.thumbnail,
                        topic.icon,
                        category.title,
                        topicKeyword.keyword
                ))
                .leftJoin(category).on(topic.categoryId.eq(category.id))
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