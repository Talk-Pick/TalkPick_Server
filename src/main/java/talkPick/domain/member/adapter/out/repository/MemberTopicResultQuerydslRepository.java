package talkPick.domain.member.adapter.out.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import talkPick.domain.member.port.out.MemberTopicResultQueryRepositoryPort;


@Repository
public class MemberTopicResultQuerydslRepository implements MemberTopicResultQueryRepositoryPort {
    private final JPAQueryFactory queryFactory;
    
    public MemberTopicResultQuerydslRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

}
