package talkPick.domain.member.adapter.out.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.adapter.out.dto.MemberResDto;
import talkPick.domain.member.port.out.MemberLikedTopicsQueryRepositoryPort;
import talkPick.domain.topic.domain.QKeyword;
import talkPick.domain.topic.domain.QTopic;
import talkPick.domain.topic.domain.QCategory;
import talkPick.domain.topic.domain.QTopicLikeHistory;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberLikedTopicsQuerydslRepository implements MemberLikedTopicsQueryRepositoryPort {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<MemberResDto.MemberLikedTopicResDto> findMemberLikedTopics(Member member, LocalDateTime cursor, int size) {
        QTopicLikeHistory tlh = QTopicLikeHistory.topicLikeHistory;
        QTopic t = QTopic.topic;
        QCategory c = QCategory.category;
        QKeyword k = QKeyword.keyword;

        // 기본 조건: 특정 회원이 좋아요한 토픽만 조회
        BooleanBuilder builder = new BooleanBuilder()
                .and(tlh.memberId.eq(member.getId()));

        // 커서 기반 페이징 조건 추가
        // cursor가 null이 아닌 경우, 해당 시간보다 이전(더 오래된) 데이터만 조회
        if (cursor != null) {
            builder.and(tlh.createdDate.lt(cursor));
        }

        // size + 1개를 조회하여 다음 페이지 존재 여부 확인
        return queryFactory
                .select(Projections.constructor(MemberResDto.MemberLikedTopicResDto.class,
                        tlh.id,
                        t.title,           // 토픽 주제 (String)
                        k.name,                  // 키워드 (Keyword)
                        c,                   // 카테고리 (Category)
                        tlh.createdDate
                ))
                .from(tlh)
                .innerJoin(t).on(tlh.topicId.eq(t.id))
                .innerJoin(c).on(t.categoryId.eq(c.id))
                .innerJoin(k).on(t.keywordId.eq(k.id))
                .where(builder)
                .orderBy(tlh.createdDate.desc()) // 최신 좋아요부터 내림차순 정렬
                .limit(size + 1) // 페이징을 위해 요청된 크기보다 1개 더 조회
                .fetch();
    }
}
