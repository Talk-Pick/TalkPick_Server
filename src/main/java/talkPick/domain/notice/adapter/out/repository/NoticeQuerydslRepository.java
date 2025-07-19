package talkPick.domain.notice.adapter.out.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import talkPick.domain.notice.adapter.in.dto.NoticeReqDTO;
import talkPick.domain.notice.adapter.out.dto.NoticeResDTO;
import talkPick.domain.notice.exception.NoticeNotFoundException;
import talkPick.global.response.CursorPageResponse;
import java.util.List;
import static talkPick.domain.notice.domain.QNotice.notice;
import static talkPick.domain.notice.domain.QNoticeImage.noticeImage;
import static talkPick.global.exception.ErrorCode.NOTICE_NOT_FOUND;

@Repository
public class NoticeQuerydslRepository {
    private final JPAQueryFactory queryFactory;
    public NoticeQuerydslRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public NoticeResDTO.NoticeDetail findNoticeDetailById(Long noticeId) {
        long updated = queryFactory.update(notice)
                .set(notice.readCount, notice.readCount.add(1))
                .where(notice.id.eq(noticeId))
                .execute();

        if (updated == 0) {
            throw new NoticeNotFoundException(NOTICE_NOT_FOUND);
        }

        var result =  queryFactory.select(Projections.constructor(NoticeResDTO.NoticeDetail.class,
                        notice.id,
                        notice.title,
                        notice.content,
                        notice.readCount,
                        notice.createdDate,
                        notice.updatedDate
                ))
                .from(notice)
                .where(notice.id.eq(noticeId))
                .fetchOne();

        if(result == null) {
            throw new NoticeNotFoundException(NOTICE_NOT_FOUND);
        }

        List<String> imageUrls = findImageUrlsByNoticeId(noticeId);
        result.addImageUrls(imageUrls);

        return result;
    }

    public CursorPageResponse<NoticeResDTO.NoticeSummary> findNoticesWithCursor(NoticeReqDTO.Cursor cursor) {
        var results = queryFactory.select(Projections.constructor(NoticeResDTO.NoticeSummary.class,
                        notice.id,
                        notice.title,
                        notice.content,
                        notice.createdDate,
                        notice.updatedDate
                ))
                .from(notice)
                .where(buildCursorPredicate(cursor))
                .orderBy(notice.createdDate.desc(), notice.id.desc())
                .limit(cursor.size() + 1)
                .fetch();

        boolean hasNext = results.size() > cursor.size();
        if (hasNext) {
            results = results.subList(0, cursor.size());
        }

        CursorPageResponse.Cursor nextCursor = null;
        if (!results.isEmpty()) {
            var last = results.getLast();
            nextCursor = new CursorPageResponse.Cursor(last.getCreatedAt(), last.getNoticeId());
        }

        return CursorPageResponse.<NoticeResDTO.NoticeSummary>builder()
                .items(results)
                .hasNext(hasNext)
                .nextCursor(nextCursor)
                .build();
    }

    private List<String> findImageUrlsByNoticeId(Long noticeId) {
        return queryFactory.select(noticeImage.imageUrl)
                .from(noticeImage)
                .where(noticeImage.noticeId.eq(noticeId))
                .fetch();
    }

    private BooleanBuilder buildCursorPredicate(NoticeReqDTO.Cursor cursor) {
        BooleanBuilder builder = new BooleanBuilder();

        if (cursor.cursorCreatedAt() != null && cursor.cursorId() != null) {
            builder.and(notice.createdDate
                    .lt(cursor.cursorCreatedAt())
                    .or(notice.createdDate
                            .eq(cursor.cursorCreatedAt())
                            .and(notice.id.lt(cursor.cursorId()))
                    )
            );
        }

        return builder;
    }
}
