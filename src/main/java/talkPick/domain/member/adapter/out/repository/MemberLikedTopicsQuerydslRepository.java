package talkPick.domain.member.adapter.out.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import talkPick.domain.member.adapter.in.dto.MemberLikedTopicsResDto;
import talkPick.domain.topic.domain.member.QMemberTopicHistory;
import talkPick.domain.topic.domain.QTopic;
import talkPick.domain.topic.domain.QCategory;
import talkPick.domain.topic.domain.QTopicKeyword;
import talkPick.domain.topic.domain.QTopicStat;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberLikedTopicsQuerydslRepository {
    private final JPAQueryFactory queryFactory;

    public Page<MemberLikedTopicsResDto> findMemberLikedTopics(Long memberId, Pageable pageable) {
        QMemberTopicHistory mth = QMemberTopicHistory.memberTopicHistory;
        QTopic t = QTopic.topic;
        QCategory c = QCategory.category;
        QTopicKeyword k = QTopicKeyword.topicKeyword;
        QTopicStat ts = QTopicStat.topicStat;

        BooleanExpression condition = mth.member.id.eq(memberId).and(mth.checkLiked.isTrue());

        // count 쿼리
        long total = queryFactory.select(t.id.countDistinct())
                .from(mth)
                .join(mth.topic, t)
                .join(c).on(t.categoryId.eq(c.id))
                .join(k).on(k.topicId.eq(t.id))
                .join(ts).on(ts.topicId.eq(t.id))
                .where(condition)
                .fetchOne();

        // 실제 데이터 쿼리
        List<MemberLikedTopicsResDto> content = queryFactory
                .select(Projections.constructor(MemberLikedTopicsResDto.class,
                        t.title,
                        ts.averageTalkTime,
                        ts.selectCount,
                        k.keyword,
                        c
                ))
                .from(mth)
                .join(mth.topic, t)
                .join(c).on(t.categoryId.eq(c.id))
                .join(k).on(k.topicId.eq(t.id))
                .join(ts).on(ts.topicId.eq(t.id))
                .where(condition)
                .distinct()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        if (content.isEmpty()) {
            throw new talkPick.global.error.exception.member.MemberNotFoundException.MemberLikedTopicsNotFoundException("해당 회원이 좋아요한 토픽을 찾을 수 없습니다. 회원 ID: " + memberId);
        }

        return new PageImpl<>(content, pageable, total);
    }
} 