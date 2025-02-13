package talkPick.domain.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import talkPick.model.BaseTime;
import talkPick.model.TalkPickStatus;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileImage extends BaseTime {
    private Long id;
    private Long profileId;
    private String url;
    private TalkPickStatus status;
}
