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
@Table(name = "notice")
public class Notice extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT COMMENT '기본 키'")
    private Long id;
    @Column(name = "admin_id", nullable = false, columnDefinition = "BIGINT COMMENT '어드민 ID'")
    private Long adminId;
    @Column(name = "title", nullable = false, length = 255, columnDefinition = "VARCHAR(255) COMMENT '공지사항 제목'")
    private String title;
    @Column(name = "content", nullable = false, columnDefinition = "TEXT COMMENT '공지사항 내용'")
    private String content;
    @Column(name = "read_count", nullable = false, columnDefinition = "INT DEFAULT 0 COMMENT '조회수'")
    private Integer readCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "VARCHAR(20) COMMENT '공지사항 상태'")
    private TalkPickStatus status;

    public static Notice of(Long adminId, String title, String content, Integer readCount, TalkPickStatus status) {
        return Notice.builder()
                .adminId(adminId)
                .title(title)
                .content(content)
                .readCount(readCount)
                .status(status)
                .build();
    }

    public void plusReadCount() {
        this.readCount++;
    }
}