package talkPick.domain.random.adapter.out.dto;

public class RandomResDTO {
    public record Categories(
            String category,
            String imageUrl
    ) {}
}
