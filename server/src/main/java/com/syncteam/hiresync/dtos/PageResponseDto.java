package com.syncteam.hiresync.dtos;

import java.util.List;

public record PageResponseDto<T>(
        int page,
        int limit,
        long totalElements,
        int totalPages,
        boolean isLast,
        List<T> content
) {
}
