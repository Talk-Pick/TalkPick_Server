package talkPick.domain.member.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import talkPick.domain.topic.domain.Category;
import talkPick.domain.topic.domain.type.Keyword;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberLikedTopicsResDto {
    private String title;  //토픽 주제 (Topic 테이블)
    private long averageTalkTime; //평균 대화 시간 (Topic 테이블)
    private long selectCount; //선택된 횟수 (Topic 테이블)
    private Keyword keyword; //키워드 (Topickeyword 테이블)
    private Category category; //카테고리 (TopicCategory 테이블)
}
