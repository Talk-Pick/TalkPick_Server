package talkPick.domain.notice.adapter.out.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import talkPick.domain.notice.adapter.out.dto.NoticeResDTO;
import talkPick.domain.notice.exception.NoticeNotFoundException;

import java.util.List;

import static talkPick.domain.notice.domain.QNotice.notice;
import static talkPick.domain.notice.domain.QNoticeImage.noticeImage;
import static talkPick.domain.topic.domain.QCategory.category;
import static talkPick.domain.topic.domain.QTopic.topic;
import static talkPick.domain.topic.domain.QTopicKeyword.topicKeyword;
import static talkPick.domain.topic.domain.QTopicStat.topicStat;
import static talkPick.global.exception.ErrorCode.NOTICE_NOT_FOUND;

@Repository
public class NoticeQuerydslRepository {
    private final JPAQueryFactory queryFactory;
    public NoticeQuerydslRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public NoticeResDTO.NoticeDetail findNoticeDetailById(Long noticeId) {
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

    private List<String> findImageUrlsByNoticeId(Long noticeId) {
        return queryFactory.select(noticeImage.imageUrl)
                .from(noticeImage)
                .where(noticeImage.noticeId.eq(noticeId))
                .fetch();
    }
}
