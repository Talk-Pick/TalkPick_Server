package talkPick.domain.topic.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    SCOUTING(
            "설레고 긴장되는 마음, 어떻게 대화를 이끌어갈까요?",
            "https://example.com/images/scouting.png"
    ),
    GROUP_MEETING(
            "대화를 시작하기도 참 애매한 상황, 아이스 브레이킹을 한 번 해볼까요?",
            "https://example.com/images/group_meeting.png"
    ),
    FAMILY(
            "가족들과 만나서 이야기할게 얼마나 많을까요?",
            "https://example.com/images/family.png"
    ),
    FRIENDS(
            "여행? 술자리? 대화가 멈추면 안돼!",
            "https://example.com/images/friends.png"
    );

    private final String description;
    private final String imageUrl;
}