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
import talkPick.domain.random.domain.QRandom;
import talkPick.domain.random.domain.type.RandomType;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberTopicResultQuerydslRepository implements MemberTopicResultQueryRepositoryPort {
    private final JPAQueryFactory queryFactory;
    QRandom random = QRandom.random;

    @Override
    public Page<MemberTopicResultResDto> findMemberTopicResults(Long memberId, LocalDate date, Pageable pageable){
        BooleanExpression condition = random.memberId.eq(memberId)
                .and(random.createdDate.between(date.atStartOfDay(), date.atTime(23, 59, 59)))
                .and(random.type.eq(RandomType.COMPLETED));

        long total = queryFactory
                .select(random.id.countDistinct())
                .from(random)
                .where(condition)
                .fetchOne();

        List<MemberTopicResultResDto> content = queryFactory
                .select(Projections.constructor(MemberTopicResultResDto.class,
                        random.id,
                        random.oneLine,
                        random.createdDate))
                .from(random)
                .where(condition)
                .orderBy(random.createdDate.asc())
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
