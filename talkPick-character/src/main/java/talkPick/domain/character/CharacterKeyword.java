package talkPick.domain.character;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import talkPick.domain.type.Keyword;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CharacterKeyword {
    private Long id;
    private Long characterId;
    private Keyword characterKeyword;
}
