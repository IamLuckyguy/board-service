package kr.co.kwt.board.application.port.in.batch;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class BatchJobResult {
    private final int processedCount;
    private final int updatedCount;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final long durationInSeconds;
}