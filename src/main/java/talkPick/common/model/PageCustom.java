package talkPick.common.model;

import java.util.List;

public record PageCustom<T> (
        List<T> content,
        int totalPages,
        long totalElements,
        int size,
        int number
) {}