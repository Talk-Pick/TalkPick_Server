package talkPick.adaptor.out.dto;

public class TopicResDTO {
    public record TopCategories(
            String category,
            String detail
    ) {}
}