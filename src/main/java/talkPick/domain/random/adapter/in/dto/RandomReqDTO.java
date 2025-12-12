package talkPick.domain.random.adapter.in.dto;

import java.time.LocalDateTime;
import java.util.List;

public class RandomReqDTO {
    public record TotalRecords(
            List<TotalRecord> totalRecords
    ) {}

    public record TotalRecord(
            Long topicId,
            Integer order,
            LocalDateTime startAt,
            LocalDateTime endAt
    ) {}

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