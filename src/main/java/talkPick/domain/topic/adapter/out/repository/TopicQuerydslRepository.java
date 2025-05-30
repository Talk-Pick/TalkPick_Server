package talkPick.domain.topic.adapter.out.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import talkPick.domain.topic.adapter.in.dto.TopicReqDTO;
import talkPick.domain.topic.adapter.out.dto.TopicResDTO;
import talkPick.domain.topic.dto.TopicDataDTO;
import java.util.List;
import static talkPick.domain.topic.domain.QCategory.category;
import static talkPick.domain.topic.domain.QTopic.topic;
import static talkPick.domain.topic.domain.QTopicKeyword.topicKeyword;
import static talkPick.domain.topic.domain.QTopicStat.topicStat;

@Repository
public class TopicQuerydslRepository {
    private final JPAQueryFactory queryFactory;
    public TopicQuerydslRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Slice<TopicResDTO.Categories> findCategoriesWithPageable(Pageable pageable) {
        var content = queryFactory
                .select(Projections.constructor(TopicResDTO.Categories.class,
                        category.id,
                        category.title,
                        category.description,
                        category.imageUrl,
                        category.categoryGroup
                ))
                .from(category)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        var hasNext = content.size() > pageable.getPageSize();
        var result = hasNext ? content.subList(0, pageable.getPageSize()) : content;

        return new SliceImpl<>(result, pageable, hasNext);
    }

    public List<TopicResDTO.TopicDetail> findTopicDetailsByIds(TopicReqDTO.TodayTopics requestDTO) {
        return queryFactory.select(Projections.constructor(TopicResDTO.TopicDetail.class,
                        topic.id,
                        topic.title,
                        topic.thumbnail,
                        topicStat.averageTalkTime,
                        topicStat.selectCount,
                        category.title,
                        category.categoryGroup,
                        topicKeyword.keyword
                ))
                .leftJoin(category).on(topic.categoryId.eq(category.id))
                .leftJoin(topicKeyword).on(topic.id.eq(topicKeyword.topicId))
                .leftJoin(topicStat).on(topic.id.eq(topicStat.topicId))
                .where(topic.id.in(requestDTO.topicIds()))
                .fetch();
    }

    public TopicResDTO.TopicDetail findTopicDetailById(Long topicId) {
        return queryFactory.select(Projections.constructor(TopicResDTO.TopicDetail.class,
                        topic.id,
                        topic.title,
                        topic.thumbnail,
                        topicStat.averageTalkTime,
                        topicStat.selectCount,
                        category.title,
                        category.categoryGroup,
                        topicKeyword.keyword
                ))
                .leftJoin(category).on(topic.categoryId.eq(category.id))
                .leftJoin(topicKeyword).on(topic.id.eq(topicKeyword.topicId))
                .leftJoin(topicStat).on(topic.id.eq(topicStat.topicId))
                .where(topic.id.eq(topicId))
                .fetchOne();
    }

    public List<TopicDataDTO> findAllTopicData() {
        return queryFactory.select(Projections.constructor(TopicDataDTO.class,
                        topic.id,
                        topic.title,
                        topic.detail,
                        topicKeyword.keyword.stringValue(),
                        topic.thumbnail,
                        topic.icon,
                        category.categoryGroup.stringValue(),
                        category.title,
                        category.description,
                        category.imageUrl,
                        topicStat.eCount,
                        topicStat.iCount,
                        topicStat.sCount,
                        topicStat.nCount,
                        topicStat.fCount,
                        topicStat.tCount,
                        topicStat.jCount,
                        topicStat.pCount,
                        topicStat.teenCount,
                        topicStat.twentiesCount,
                        topicStat.thirtiesCount,
                        topicStat.fortiesCount,
                        topicStat.fiftiesCount,
                        topicStat.maleCount,
                        topicStat.femaleCount,
                        topicStat.selectCount,
                        topicStat.averageTalkTime
                ))
                .from(topic)
                .join(topicStat).on(topic.id.eq(topicStat.topicId))
                .join(topicKeyword).on(topic.id.eq(topicKeyword.topicId))
                .join(category).on(topic.categoryId.eq(category.id))
                .fetch();
    }
}