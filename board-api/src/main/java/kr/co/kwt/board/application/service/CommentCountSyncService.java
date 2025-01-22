package kr.co.kwt.board.application.service;

import kr.co.kwt.board.application.port.in.batch.BatchJobResult;
import kr.co.kwt.board.application.port.in.batch.SyncCommentCountUseCase;
import kr.co.kwt.board.application.port.out.comment.LoadCommentPort;
import kr.co.kwt.board.application.port.out.post.LoadPostPort;
import kr.co.kwt.board.application.port.out.post.SavePostPort;
import kr.co.kwt.board.application.service.exception.BatchJobException;
import kr.co.kwt.board.common.exception.ErrorCode;
import kr.co.kwt.board.domain.post.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentCountSyncService implements SyncCommentCountUseCase {
    private final LoadCommentPort loadCommentPort;
    private final LoadPostPort loadPostPort;
    private final SavePostPort savePostPort;

    @Override
    @Transactional
    public BatchJobResult sync() {
        LocalDateTime startTime = LocalDateTime.now();
        int processedCount = 0;
        int updatedCount = 0;

        try {
            log.info("Starting comment count sync job at {}", startTime);

            List<Post> posts = loadPostPort.loadAll();
            processedCount = posts.size();

            for (Post post : posts) {
                int actualCommentCount = loadCommentPort.countActiveCommentsByPostId(post.getId());
                if (post.getCommentCount() != actualCommentCount) {
                    post.syncCommentCount(actualCommentCount);
                    savePostPort.save(post);
                    updatedCount++;
                }
            }

            LocalDateTime endTime = LocalDateTime.now();
            long durationInSeconds = ChronoUnit.SECONDS.between(startTime, endTime);

            log.info("Comment count sync job completed at {}. Processed: {}, Updated: {}, Duration: {} seconds",
                    endTime, processedCount, updatedCount, durationInSeconds);

            return BatchJobResult.builder()
                    .processedCount(processedCount)
                    .updatedCount(updatedCount)
                    .startTime(startTime)
                    .endTime(endTime)
                    .durationInSeconds(durationInSeconds)
                    .build();

        } catch (Exception e) {
            log.error("Error during comment count sync job", e);
            throw new BatchJobException(
                    ErrorCode.BATCH_JOB_FAILED,
                    String.format("댓글 수 동기화 작업 중 오류가 발생했습니다: %s", e.getMessage()),
                    e
            );
        }
    }
}