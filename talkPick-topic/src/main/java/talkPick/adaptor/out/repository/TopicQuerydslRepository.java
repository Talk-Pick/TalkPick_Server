package talkPick.adaptor.out.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import talkPick.adaptor.out.dto.TopicResDTO;
import talkPick.domain.QTopicCategory;
import talkPick.domain.TopicCategory;
import talkPick.domain.type.Category;
import talkPick.model.PageCustom;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static talkPick.domain.QTopicCategory.topicCategory;

@Repository
public class TopicQuerydslRepository {
    private final JPAQueryFactory queryFactory;
    public TopicQuerydslRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Category> findTopCategories(int count) {
        return queryFactory
                .select(topicCategory.category)
                .from(topicCategory)
                .groupBy(topicCategory.category)
                .orderBy(topicCategory.category.count().desc())
                .limit(count)
                .fetch();
    }

    public PageCustom<TopicResDTO.Categories> findCategoriesWithPageable(Pageable pageable) {
        long totalElements = Optional.ofNullable(
                queryFactory.select(topicCategory.category.countDistinct())
                        .select(topicCategory.category.countDistinct())
                        .from(topicCategory)
                        .fetchOne()
        ).orElse(0L);

        List<TopicResDTO.Categories> content = queryFactory
                .select(topicCategory.category)
                .from(topicCategory)
                .groupBy(topicCategory.category)
                .orderBy(topicCategory.category.count().desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(category -> new TopicResDTO.Categories(category.name(), category.getDescription()))
                .collect(Collectors.toList());

        int totalPages = (int) Math.ceil((double) totalElements / pageable.getPageSize());
        return new PageCustom<>(content, totalPages, totalElements, pageable.getPageSize(), pageable.getPageNumber());
    }
}