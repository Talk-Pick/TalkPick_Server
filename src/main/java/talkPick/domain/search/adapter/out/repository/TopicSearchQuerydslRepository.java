package talkPick.domain.search.adapter.out.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class TopicSearchQuerydslRepository {
    private final JPAQueryFactory queryFactory;
    public TopicSearchQuerydslRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
}