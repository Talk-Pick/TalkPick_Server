package talkPick.domain.random.adapter.out.dto;

public class RandomResDTO {
    public record Categories(
            String category,
            String imageUrl
    ) {}

    public record RandomTopic(
            Integer order,
            Long topicId,
            String category,
            String imageUrl,
            String keyword,
            String thumbnail,
            String icon
    ) {}
}
