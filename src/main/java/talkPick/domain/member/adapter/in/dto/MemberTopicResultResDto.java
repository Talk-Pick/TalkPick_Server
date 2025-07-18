package talkPick.domain.member.adapter.in.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberTopicResultResDto {
    private Long randomId;
    private String comment;
    private LocalDateTime createdDate;
}