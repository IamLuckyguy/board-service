package kr.co.kwt.board.application.port.in.post;

import kr.co.kwt.board.domain.post.PostType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public interface CreatePostUseCase {
    Long createPost(CreatePostCommand command);

    @Getter
    @Builder
    class CreatePostCommand {
        private final Long serviceId;
        private final Long authorId;
        private final String title;
        private final String content;
        private final PostType postType;
        private final boolean isPinned;
        private final LocalDateTime scheduledAt;
        private final Long createdBy;
    }
}