package talkPick.domain.member.adapter.out.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import talkPick.domain.member.adapter.in.dto.MemberTopicResultResDto;
import talkPick.domain.topic.domain.member.QMemberTopicResult;
import talkPick.global.error.exception.member.MemberNotFoundException;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberTopicResultQuerydslRepository {
    private final JPAQueryFactory queryFactory;
    QMemberTopicResult mtr = QMemberTopicResult.memberTopicResult;

    public Page<MemberTopicResultResDto> findMemberTopicResults(Long memberId, LocalDate date, Pageable pageable){
        BooleanExpression condition = mtr.member.id.eq(memberId)
            .and(mtr.createdDate.year().eq(date.getYear()))
            .and(mtr.createdDate.month().eq(date.getMonthValue()))
            .and(mtr.createdDate.dayOfMonth().eq(date.getDayOfMonth()));

        long total = queryFactory
                .select(mtr.id.countDistinct())
                .from(mtr)
                .where(condition)
                .fetchOne();

        List<MemberTopicResultResDto> content = queryFactory
                .select(Projections.constructor(MemberTopicResultResDto.class,
                        mtr.comment,
                        mtr.createdDate))
                .from(mtr)
                .where(condition)
                .orderBy(mtr.createdDate.asc())
                .distinct()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        if (content.isEmpty()) {
            throw new MemberNotFoundException.MemberTopicResultsNotFoundException("해당 회원의 토픽 결과를 찾을 수 없습니다. 회원 ID: " + memberId);
        }

        return new PageImpl<>(content, pageable, total);
    }
}
