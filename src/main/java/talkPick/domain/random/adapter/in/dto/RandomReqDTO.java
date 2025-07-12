package talkPick.domain.random.adapter.in.dto;

public class RandomReqDTO {
    public record SelectCategory(
            Long randomId,
            String categoryGroup,
            String category
    ) {}

    public record SelectTopic(
            Long randomId,
            Long topicId,
            String categoryGroup,
            String category,
            String keyword,
            Integer order
    ) {}

    public record Result (
            String title,
            String comment
    ) {}
}