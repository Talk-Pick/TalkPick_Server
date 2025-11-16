package talkPick.domain.topic.adapter.out.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import talkPick.domain.topic.adapter.out.dto.TopicResDTO;
import talkPick.domain.topic.domain.type.CategoryGroup;
import java.util.List;
import static talkPick.domain.topic.domain.QCategory.category;
import static talkPick.domain.topic.domain.QKeyword.keyword;
import static talkPick.domain.topic.domain.QTopic.topic;
import static talkPick.domain.topic.domain.QTopicStat.topicStat;

@Repository
@RequiredArgsConstructor
public class TopicQuerydslRepository {
    private final JPAQueryFactory queryFactory;

    public List<TopicResDTO.Categories> findCategoriesByCategoryGroup(CategoryGroup categoryGroup) {
        return queryFactory.select(Projections.constructor(TopicResDTO.Categories.class,
                        category.id,
                        category.title,
                        category.imageUrl,
                        category.categoryGroup
                ))
                .from(category)
                .where(category.categoryGroup.eq(categoryGroup))
                .fetch();
    }

    public TopicResDTO.TopicDetail findTopicDetailById(Long topicId) {
        return queryFactory.select(Projections.constructor(TopicResDTO.TopicDetail.class,
                        topic.id,
                        topic.title,
                        category.title,
                        category.categoryGroup,
                        keyword.name,
                        keyword.imageUrl
                ))
                .from(topic)
                .leftJoin(category).on(topic.categoryId.eq(category.id))
                .leftJoin(keyword).on(topic.keywordId.eq(keyword.id))
                .leftJoin(topicStat).on(topic.id.eq(topicStat.topicId))
                .where(topic.id.eq(topicId))
                .fetchOne();
    }
}