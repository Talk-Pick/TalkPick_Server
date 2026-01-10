package talkPick.domain.topic.adapter.out.dto;

public class TopicResDTO {
    public record Topic(
            Long id,
            String title
    ){}

    public record Categories(
            Long categoryId,
            String title,
            String imageUrl
    ) {}

    public record TopicDetail(
            Long topicId,
            String title,
            String detail,
            String category,
            String keywordName,
            String keywordImageUrl,
            String topicImageUrl
    ) {}
}