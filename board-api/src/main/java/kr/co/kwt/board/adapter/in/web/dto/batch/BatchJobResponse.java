package kr.co.kwt.board.adapter.in.web.dto.batch;

import java.time.LocalDateTime;

public record BatchJobResponse(
        int processedCount,
        int updatedCount,
        LocalDateTime startTime,
        LocalDateTime endTime,
        long durationInSeconds
) {
}