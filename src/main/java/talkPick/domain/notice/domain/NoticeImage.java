package talkPick.domain.notice.domain;

import jakarta.persistence.*;
import lombok.*;
import talkPick.global.model.BaseTime;
import talkPick.global.model.TalkPickStatus;


@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "공지사항 이미지 테이블")
public class NoticeImage extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT COMMENT '기본 키'")
    private Long id;

    @Column(name = "notice_id", nullable = false, columnDefinition = "BIGINT COMMENT '공지사항 ID'")
    private Long noticeId;

    @Column(name = "image_url", nullable = false, length = 500, columnDefinition = "VARCHAR(500) COMMENT '이미지 URL'")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "VARCHAR(20) COMMENT '이미지 상태'")
    private TalkPickStatus status;

    public static NoticeImage of(Long noticeId, String imageUrl, TalkPickStatus status) {
        return NoticeImage.builder()
                .noticeId(noticeId)
                .imageUrl(imageUrl)
                .status(status)
                .build();
    }
}