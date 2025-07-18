package talkPick.domain.notice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import talkPick.global.model.BaseTime;
import talkPick.global.model.TalkPickStatus;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeImage extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long noticeId;
    private String imageUrl;
    private TalkPickStatus status;

    public static NoticeImage of(Long noticeId, String imageUrl, TalkPickStatus status) {
        return NoticeImage.builder()
                .noticeId(noticeId)
                .imageUrl(imageUrl)
                .status(status)
                .build();
    }
}
