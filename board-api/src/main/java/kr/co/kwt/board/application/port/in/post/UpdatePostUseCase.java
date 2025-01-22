package kr.co.kwt.board.application.port.in.post;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

public interface UpdatePostUseCase {
    void updatePost(UpdatePostCommand command);

    @Getter
    @Builder
    class UpdatePostCommand {
        private final Long postId;
        private final String title;
        private final String content;
        private final boolean isPinned;
        private final LocalDateTime scheduledAt;
        private final Long updatedBy;
    }
}