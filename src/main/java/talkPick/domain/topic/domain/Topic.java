package talkPick.domain.topic.domain;

import jakarta.persistence.*;
import lombok.*;
import talkPick.global.common.model.BaseTime;
import talkPick.global.common.model.TalkPickStatus;
import talkPick.domain.admin.domain.Admin;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Topic extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String detail;
    private String thumbnail;
    private String icon;
    private Long categoryId;
    @Enumerated(EnumType.STRING)
    private TalkPickStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Admin createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id", insertable = false, updatable = false)
    private talkPick.domain.topic.domain.Category category;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", referencedColumnName = "id", insertable = false, updatable = false)
    private List<TopicKeyword> topicKeywords = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", referencedColumnName = "id", insertable = false, updatable = false)
    private TopicStat topicStat;

    public static Topic create(String title, String detail, String thumbnail, String icon, Admin createdBy) {
        //TODO category_id 넣어줘야 함.
        return Topic.builder()
                .title(title)
                .detail(detail)
                .thumbnail(thumbnail)
                .icon(icon)
                .createdBy(createdBy)
                .status(TalkPickStatus.ACTIVE) // 상태 초기화
                .build();
    }

    public void update(String title, String detail, String thumbnail, String icon) {
        this.title = title;
        this.detail = detail;
        this.thumbnail = thumbnail;
        this.icon = icon;
    }
}