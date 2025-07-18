package talkPick.domain.member.adapter.out.repository.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import talkPick.domain.member.adapter.in.dto.MemberTopicResultResDto;
import talkPick.domain.member.port.out.MemberTopicResultQueryRepositoryPort;
import talkPick.domain.topic.domain.member.QMemberTopicResult;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberTopicResultQuerydslRepository implements MemberTopicResultQueryRepositoryPort {
    private final JPAQueryFactory queryFactory;
    QMemberTopicResult mtr = QMemberTopicResult.memberTopicResult;

    @Override
    public Page<MemberTopicResultResDto> findMemberTopicResults(Long memberId, LocalDate date, Pageable pageable){
        BooleanExpression condition = mtr.member.id.eq(memberId)
                .and(mtr.createdDate.between(date.atStartOfDay(), date.atTime(23, 59, 59)));

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
            return new PageImpl<>(content, pageable, total);
        }

        return new PageImpl<>(content, pageable, total);
    }
}
