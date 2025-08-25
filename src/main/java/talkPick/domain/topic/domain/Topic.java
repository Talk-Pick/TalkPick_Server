package talkPick.domain.topic.domain;

import jakarta.persistence.*;
import lombok.*;
import talkPick.global.model.BaseTime;
import talkPick.global.model.TalkPickStatus;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Topic 테이블")
public class Topic extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT COMMENT '기본 키'")
    private Long id;

    @Column(name = "title", nullable = false, length = 255, columnDefinition = "VARCHAR(255) COMMENT 'Topic 제목'")
    private String title;

    @Column(name = "detail", nullable = false, length = 1000, columnDefinition = "VARCHAR(1000) COMMENT 'Topic 상세 내용'")
    private String detail;

    @Column(name = "category_id", nullable = false, columnDefinition = "BIGINT COMMENT '카테고리 ID'")
    private Long categoryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "VARCHAR(20) COMMENT 'Topic 상태'")
    private TalkPickStatus status;

    @Column(name = "admin_id", nullable = false, columnDefinition = "BIGINT COMMENT '어드민 ID'")
    private Long adminId;

    public static Topic create(String title, String detail, Long adminId) {
        return Topic.builder()
                .title(title)
                .detail(detail)
                .status(TalkPickStatus.ACTIVE)
                .adminId(adminId)
                .build();
    }

    public static Topic of(String title, String detail, Long categoryId, Long adminId) {
        return Topic.builder()
                .title(title)
                .detail(detail)
                .categoryId(categoryId)
                .status(TalkPickStatus.ACTIVE)
                .adminId(adminId)
                .build();
    }

    public void update(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }
}