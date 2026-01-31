package talkPick.domain.topic.domain;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "image_url", nullable = false, length = 500, columnDefinition = "VARCHAR(500) COMMENT '카테고리 이미지 URL'")
    private String imageUrl;

    @Column(name = "color", nullable = false, length = 255, columnDefinition = "VARCHAR(500) COMMENT '카테고리 색상'")
    private String color;
}