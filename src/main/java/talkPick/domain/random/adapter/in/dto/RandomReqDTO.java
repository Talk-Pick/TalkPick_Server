package talkPick.domain.random.adapter.in.dto;

public class RandomReqDTO {
    public record SelectCategory(
            Long randomId
    ) {}

    public record SelectTopic(
            Long randomId,
            Long topicId,
            Integer order
    ) {}

    public record Result (
            String title,
            String comment
    ) {}
}