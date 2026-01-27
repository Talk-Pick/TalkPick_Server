package talkPick.domain.notice.domain;

import jakarta.persistence.*;
import lombok.*;
import talkPick.core.common.model.BaseTime;
import talkPick.core.common.model.TalkPickStatus;


@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "notice_image")
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