package talkPick.domain.topic.domain;

import jakarta.persistence.*;
import lombok.*;
import talkPick.domain.topic.domain.type.CategoryGroup;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT COMMENT '기본 키'")
    private Long id;

    @Column(name = "title", nullable = false, length = 255, columnDefinition = "VARCHAR(255) COMMENT '카테고리 제목'")
    private String title;

    @Column(name = "image_url", nullable = true, length = 500, columnDefinition = "VARCHAR(500) COMMENT '카테고리 이미지 URL'")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_group", nullable = false, columnDefinition = "VARCHAR(20) COMMENT '카테고리 그룹'")
    private CategoryGroup categoryGroup;

    public static Category of(String title, String imageUrl, CategoryGroup categoryGroup) {
        return Category.builder()
                .title(title)
                .imageUrl(imageUrl)
                .categoryGroup(categoryGroup)
                .build();
    }
}