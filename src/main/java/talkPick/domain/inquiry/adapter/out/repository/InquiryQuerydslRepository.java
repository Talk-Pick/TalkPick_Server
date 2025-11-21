package talkPick.domain.inquiry.adapter.out.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import talkPick.domain.inquiry.adapter.out.dto.InquiryResDto;
import talkPick.domain.inquiry.domain.Inquiry;
import talkPick.domain.inquiry.domain.QInquiry;
import talkPick.domain.member.domain.Member;
import talkPick.domain.inquiry.port.out.InquiryQueryRepositoryPort;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class InquiryQuerydslRepository {
    private final JPAQueryFactory queryFactory;

    public List<InquiryResDto.InquiryListItemResDto> findMyInquiries(Member member, LocalDateTime cursor, int size) {
        QInquiry iq = QInquiry.inquiry;

        BooleanBuilder builder = new BooleanBuilder()
                .and(iq.memberId.eq(member.getId()));

        if (cursor != null) {
            builder.and(iq.createdDate.lt(cursor));
        }

        return queryFactory
                .select(Projections.constructor(InquiryResDto.InquiryListItemResDto.class,
                        iq.id,
                        iq.title,
                        iq.type,
                        iq.isAnswered,
                        iq.createdDate
                ))
                .from(iq)
                .where(builder)
                .orderBy(iq.createdDate.desc())
                .limit(size + 1)
                .fetch();
    }
}


