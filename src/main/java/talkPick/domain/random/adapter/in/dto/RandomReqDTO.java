package talkPick.domain.random.adapter.in.dto;

public class RandomReqDTO {
    public record SelectCategory(
            Long randomId,
            String category
    ) {}
}