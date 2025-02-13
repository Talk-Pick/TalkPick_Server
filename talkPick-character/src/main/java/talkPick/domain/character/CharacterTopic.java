package talkPick.domain.character;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CharacterTopic {
    private Long id;
    private Long characterId;
    private Long topicId;
    private String topic;
    private long talkTime;
}
