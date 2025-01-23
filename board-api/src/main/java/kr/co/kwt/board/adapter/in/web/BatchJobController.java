package kr.co.kwt.board.adapter.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "배치 작업 API", description = "시스템 배치 작업 API")
@RequiredArgsConstructor
public class BatchJobController {
    private final SyncCommentCountUseCase syncCommentCountUseCase;

    @Operation(summary = "댓글 수 동기화", description = "게시글의 댓글 수를 동기화합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "동기화 성공"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
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