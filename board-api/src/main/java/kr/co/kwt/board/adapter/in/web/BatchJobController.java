package kr.co.kwt.board.adapter.in.web;

import kr.co.kwt.board.adapter.in.web.dto.batch.BatchJobResponse;
import kr.co.kwt.board.application.port.in.batch.BatchJobResult;
import kr.co.kwt.board.application.port.in.batch.SyncCommentCountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/batch")
@RequiredArgsConstructor
public class BatchJobController {
    private final SyncCommentCountUseCase syncCommentCountUseCase;

    @PostMapping("/sync-comment-count")
    public ResponseEntity<BatchJobResponse> syncCommentCount() {
        BatchJobResult result = syncCommentCountUseCase.sync();
        return ResponseEntity.ok(new BatchJobResponse(
                result.getProcessedCount(),
                result.getUpdatedCount(),
                result.getStartTime(),
                result.getEndTime(),
                result.getDurationInSeconds()
        ));
    }
}