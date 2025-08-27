package talkPick.domain.member.adapter.out.repository;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.querydsl.core.group.GroupBy.*;

@Repository
public class MemberTopicResultQuerydslRepository implements MemberTopicResultQueryRepositoryPort {
    private final JPAQueryFactory queryFactory;

    public MemberTopicResultQuerydslRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<MemberResDto.MemberTopicResultResDto> findMemberTopicResults(Member member, LocalDate date, LocalDateTime cursor, int size) {
        QMemberTopicHistory mth = QMemberTopicHistory.memberTopicHistory;
        QMemberTopicResult mtr = QMemberTopicResult.memberTopicResult;
        QTopicKeyword tk = QTopicKeyword.topicKeyword;

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime nextDayStart = date.plusDays(1).atStartOfDay();

        // 커서 조건: cursor 이전(createdDate)만 조회
        // createdDate가 동일할 경우를 대비해 id를 세컨더리 정렬 키로 사용
        return queryFactory
                .from(mth)
                .leftJoin(mtr).on(mtr.id.eq(mth.member_topic_result_id))
                .leftJoin(tk).on(tk.topicId.eq(mth.topicId))
                .where(
                        mth.memberId.eq(member.getId())
                                .and(mth.member_topic_result_id.isNotNull())
                                .and(mth.createdDate.goe(startOfDay))
                                .and(mth.createdDate.lt(nextDayStart))
                                .and(cursor != null ? mth.createdDate.lt(cursor) : null)
                )
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
