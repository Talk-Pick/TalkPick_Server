package talkPick.domain;

import jakarta.persistence.*;
import lombok.*;
import talkPick.domain.type.Keyword;
import talkPick.model.BaseTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TopicKeyword extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long topicId;
    @Enumerated(EnumType.STRING)
    @Column(name = "key_word")
    private Keyword keyword;
}
