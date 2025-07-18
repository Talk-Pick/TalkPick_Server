package talkPick.domain.member.adapter.out.repository.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import talkPick.domain.member.adapter.in.dto.MemberLikedTopicsResDto;
import talkPick.domain.member.port.out.MemberLikedTopicsQueryRepositoryPort;
import talkPick.domain.topic.domain.QCategory;
import talkPick.domain.topic.domain.QTopic;
import talkPick.domain.topic.domain.QTopicKeyword;
import talkPick.domain.topic.domain.QTopicStat;
import talkPick.domain.topic.domain.member.QMemberTopicHistory;

import java.util.List;

@Repository
public class MemberLikedTopicsQuerydslRepository implements MemberLikedTopicsQueryRepositoryPort {
    private final JPAQueryFactory queryFactory;
    public MemberLikedTopicsQuerydslRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    @Override
    public Page<MemberLikedTopicsResDto> findMemberLikedTopics(Long memberId, Pageable pageable) {
        QMemberTopicHistory mth = QMemberTopicHistory.memberTopicHistory;
        QTopic t = QTopic.topic;
        QCategory c = QCategory.category;
        QTopicKeyword k = QTopicKeyword.topicKeyword;
        QTopicStat ts = QTopicStat.topicStat;

        BooleanExpression condition = mth.member.id.eq(memberId).and(mth.checkLiked.isTrue());

        // 전체 개수 쿼리 (countDistinct로 중복 방지)
        long total = queryFactory
                .select(t.id.countDistinct())
                .from(mth)
                .join(mth.topic, t)
                .where(condition)
                .fetchOne();

        // 실제 데이터 쿼리
        List<MemberLikedTopicsResDto> content = queryFactory
                .select(Projections.constructor(MemberLikedTopicsResDto.class,
                        t.title,
                        ts.averageTalkTime,
                        ts.selectCount,
                        k.keyword,
                        c.categoryGroup
                ))
                .from(mth)
                .join(mth.topic, t)
                .join(ts).on(ts.topicId.eq(t.id))
                .join(k).on(k.topicId.eq(t.id))
                .join(c).on(t.categoryId.eq(c.id))
                .where(condition)
                .distinct()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 빈 결과는 예외 대신 빈 페이지 반환
        return new PageImpl<>(content, pageable, total);
    }
}
