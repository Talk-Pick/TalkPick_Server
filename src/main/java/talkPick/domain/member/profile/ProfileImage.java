package talkPick.domain.member.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import talkPick.common.model.BaseTime;
import talkPick.common.model.TalkPickStatus;

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
