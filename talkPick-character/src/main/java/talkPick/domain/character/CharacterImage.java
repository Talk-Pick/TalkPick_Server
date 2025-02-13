package talkPick.domain.character;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CharacterImage {
    private Long characterImageId;
    private Long characterId;
    private String url;
}
