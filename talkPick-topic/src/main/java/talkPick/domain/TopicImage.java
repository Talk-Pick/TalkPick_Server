package talkPick.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import talkPick.model.TalkPickStatus;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopicImage {
    private Long id;
    private Long topicId;
    private String imageUrl;
    private TalkPickStatus status;
}
