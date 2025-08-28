package talkPick.domain.member.adapter.out.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import talkPick.domain.member.adapter.out.dto.MemberResDto;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.port.out.MemberTopicResultQueryRepositoryPort;
import talkPick.domain.topic.domain.*;
import talkPick.domain.topic.domain.member.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.querydsl.core.group.GroupBy.*;

@Repository
public class MemberTopicResultQuerydslRepository implements MemberTopicResultQueryRepositoryPort {
    private final JPAQueryFactory queryFactory;

    public MemberTopicResultQuerydslRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<MemberResDto.MemberTopicResultResDto> findMemberTopicResults(Member member, LocalDate date,
                                                                             LocalDateTime cursor, int size) {
        QMemberTopicHistory mth = QMemberTopicHistory.memberTopicHistory;
        QMemberTopicResult mtr = QMemberTopicResult.memberTopicResult;
        QTopicKeyword tk = QTopicKeyword.topicKeyword;

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime nextDayStart = date.plusDays(1).atStartOfDay();

        // where 조건 누적
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(mth.memberId.eq(member.getId()));
        builder.and(mth.member_topic_result_id.isNotNull());
        builder.and(mth.createdDate.goe(startOfDay));
        builder.and(mth.createdDate.lt(nextDayStart));

        // 커서 기반 페이징 조건 추가
        // cursor가 null이 아닌 경우, 해당 시간보다 이전(더 오래된) 데이터만 조회
        if (cursor != null) {
            builder.and(mth.createdDate.lt(cursor));
        }

        // size + 1개를 조회하여 다음 페이지 존재 여부 확인
        return queryFactory
                .from(mth)
                .leftJoin(mtr).on(mtr.id.eq(mth.member_topic_result_id))
                .leftJoin(tk).on(tk.topicId.eq(mth.topicId))
                .where(builder)
                .orderBy(mth.createdDate.desc(), mth.id.desc())
                .limit(size + 1)
                .transform(groupBy(mth.id).list(
                        Projections.constructor(
                                MemberResDto.MemberTopicResultResDto.class,
                                mth.id,
                                mtr.comment,
                                list(tk.keyword),
                                mth.createdDate
                        )
                ));
    }
}
