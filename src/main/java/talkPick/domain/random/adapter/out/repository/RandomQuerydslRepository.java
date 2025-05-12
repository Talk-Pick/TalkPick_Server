package talkPick.domain.random.adapter.out.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import java.util.List;
import java.util.stream.Collectors;
import static talkPick.domain.topic.domain.QTopicCategory.topicCategory;

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
}