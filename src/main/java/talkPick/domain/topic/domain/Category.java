package talkPick.domain.topic.domain;

import jakarta.persistence.*;
import lombok.*;
import talkPick.domain.topic.domain.type.CategoryGroup;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private CategoryGroup categoryGroup;
}