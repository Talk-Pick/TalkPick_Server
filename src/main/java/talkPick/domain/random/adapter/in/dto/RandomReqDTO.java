package talkPick.domain.random.adapter.in.dto;

public class RandomReqDTO {
    public record SelectByCategory(
            Long randomId
    ) {}

    public record SelectByTopic(
            Long randomId,
            Long topicId,
            Integer order
    ) {}

    public record Rate(
            Integer rating
    ) {}

    public record Comment(
            String oneLine
    ) {}
}