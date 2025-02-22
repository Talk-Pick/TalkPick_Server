package talkPick.adaptor.dto.response;

public class TopicResDTO {
    public record Categories(
            String category,
            String detail
    ) {}
}