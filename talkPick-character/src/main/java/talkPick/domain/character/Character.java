package talkPick.domain.character;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import talkPick.domain.type.RelationshipDuration;
import talkPick.domain.type.TemperatureIndex;
import talkPick.model.BaseTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Character extends BaseTime {
    private Long id;
    private Long userId;
    private TemperatureIndex temperatureIndex;
    private RelationshipDuration relationshipDuration;
    private long totalTalkTime;
}
