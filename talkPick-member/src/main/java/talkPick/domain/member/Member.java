package talkPick.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import talkPick.domain.type.Gender;
import talkPick.model.BaseTime;
import talkPick.model.TalkPickStatus;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseTime {
    private Long id;
    private String email;
    private String password;
    private String birth;
    private Gender gender;
    private TalkPickStatus status;
}