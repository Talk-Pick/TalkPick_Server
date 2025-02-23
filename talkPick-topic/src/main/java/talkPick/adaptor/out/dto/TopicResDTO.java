package talkPick.adaptor.out.dto;

public class TopicResDTO {
    public record Categories(
            String category,
            String description
    ) {}
}