package talkPick.adaptor.out.repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import talkPick.domain.type.Category;
import java.util.List;

import static talkPick.domain.QTopic.topic;

@Repository
public class TopicQuerydslRepository {
    private final JPAQueryFactory queryFactory;
    public TopicQuerydslRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Category> getTopCategories(int count) {
        return null;
    }
}