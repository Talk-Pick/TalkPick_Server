package talkPick.domain.member.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import talkPick.domain.member.domain.type.MBTI;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberMbtiUpdateRequestDto {
    private MBTI mbti;
}
