package talkPick.domain.random.adapter.in.dto;

public class RandomReqDTO {
    public record Next(
            Integer order
    ) {}

    public record Record(
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