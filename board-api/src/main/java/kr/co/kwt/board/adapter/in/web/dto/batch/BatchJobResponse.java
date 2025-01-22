package kr.co.kwt.board.adapter.in.web.dto.batch;

import lombok.Value;
import java.time.LocalDateTime;

@Value
public class BatchJobResponse {
    int processedCount;
    int updatedCount;
    LocalDateTime startTime;
    LocalDateTime endTime;
    long durationInSeconds;
}