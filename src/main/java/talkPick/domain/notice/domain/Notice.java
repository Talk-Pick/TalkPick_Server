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
public class Notice extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long adminId;
    private String title;
    private String content;
    private Integer readCount;

    @Enumerated(EnumType.STRING)
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