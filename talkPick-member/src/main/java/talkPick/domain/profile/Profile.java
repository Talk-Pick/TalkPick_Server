package talkPick.domain.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import talkPick.domain.type.MBTI;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    private Long id;
    private Long memberId;
    private String nickname;
    private MBTI mbti;
}