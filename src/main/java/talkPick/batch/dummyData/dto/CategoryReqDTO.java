package talkPick.batch.dummyData.dto;

import talkPick.domain.topic.domain.type.CategoryGroup;

public class CategoryReqDTO {
    public record Create(
            String title,
            String description,
            String imageUrl,
            CategoryGroup categoryGroup
    ) {
        public static Create of(String title, String description, String imageUrl, CategoryGroup categoryGroup) {
            return new Create(title, description, imageUrl, categoryGroup);
        }
    }
}
