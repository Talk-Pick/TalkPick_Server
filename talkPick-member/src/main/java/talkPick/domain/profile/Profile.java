package talkPick.domain.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import talkPick.domain.type.MBTI;
import talkPick.model.BaseTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Profile extends BaseTime {
    private Long id;
    private Long memberId;
    private MBTI mbti;
}