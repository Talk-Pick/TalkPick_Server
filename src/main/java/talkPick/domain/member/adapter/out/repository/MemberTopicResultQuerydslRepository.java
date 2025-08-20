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
//
//    QRandom r = QRandom.random;
//    QTopic t = QTopic.topic;
//    QTopicKeyword tk = QTopicKeyword.topicKeyword;
//    QMemberTopicHistory mth = QMemberTopicHistory.memberTopicHistory;
//    QRandomTopicHistory rth = QRandomTopicHistory.randomTopicHistory;
//
//    @Override
//    public List<MemberResDto.MemberTopicResultResDto> findMemberTopicResults(Member member, LocalDate date) {
//        // 일반 토픽 대화 기록 조회 (MemberTopicHistory)
//        List<MemberResDto.MemberTopicResultResDto> memberTopicResults = queryFactory
//                .select(Projections.constructor(MemberResDto.MemberTopicResultResDto.class,
//                        memberTopicResult.comment,
//                        tk.keyword,
//                        mth.createdDate))
//                .from(mth)
//                .leftJoin(memberTopicResult).on(
//                        memberTopicResult.member.eq(member)
//                        .and(memberTopicResult.id.eq(mth.id))
//                )
//                .innerJoin(t).on(mth.topic.eq(t))
//                .innerJoin(tk).on(tk.topicId.eq(t.id))
//                .where(mth.member.eq(member)
//                        .and(mth.createdDate.goe(date.atStartOfDay()))
//                        .and(mth.createdDate.lt(date.plusDays(1).atStartOfDay())))
//                .orderBy(mth.createdDate.asc())
//                .fetch();
//
//        // 랜덤 토픽 대화 기록 조회 (RandomTopicHistory)
//        List<MemberResDto.MemberTopicResultResDto> randomTopicResults = queryFactory
//                .select(Projections.constructor(MemberResDto.MemberTopicResultResDto.class,
//                        memberTopicResult.comment,
//                        tk.keyword,
//                        rth.startAt))
//                .from(rth)
//                .leftJoin(memberTopicResult).on(
//                        memberTopicResult.member.eq(member)
//                        .and(memberTopicResult.id.eq(rth.id))
//                )
//                .innerJoin(t).on(rth.topicId.eq(t.id))
//                .innerJoin(tk).on(tk.topicId.eq(t.id))
//                .where(rth.memberId.eq(member.getId())
//                        .and(rth.startAt.goe(date.atStartOfDay()))
//                        .and(rth.startAt.lt(date.plusDays(1).atStartOfDay())))
//                .orderBy(rth.startAt.asc())
//                .fetch();
//
//        // 두 결과를 합치고 날짜순으로 정렬
//        memberTopicResults.addAll(randomTopicResults);
//        memberTopicResults.sort((a, b) -> a.getCreatedDate().compareTo(b.getCreatedDate()));
//
//        return memberTopicResults;
//    }
}
