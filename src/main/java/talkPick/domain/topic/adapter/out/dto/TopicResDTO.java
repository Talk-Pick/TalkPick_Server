package talkPick.domain.topic.adapter.out.dto;

public class TopicResDTO {
    public record Topic(
            Long id,
            String title
    ){}
    public record TopicSummaries(
            Long topicId,
            String title,
            String icon,
            long averageTalkTime,
            int selectCount,
            String category,
            String categoryGroup,
            String keyword
    ) {}
    public record TopicDetail(
            Long topicId,
            String title,
            String thumbnail,
            long averageTalkTime,
            int selectCount,
            String category,
            String categoryGroup,
            String keyword
    ) {}
    public record Categories(
            Long categoryId,
            String title,
            String description,
            String imageUrl,
            String categoryGroup
    ) {}
}