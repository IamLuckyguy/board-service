package kr.co.kwt.board.application.port.in.comment;

import lombok.Builder;
import lombok.Getter;

public interface CreateCommentUseCase {
    Long createComment(CreateCommentCommand command);

    @Getter
    @Builder
    class CreateCommentCommand {
        private final Long postId;
        private final Long parentId;
        private final Long authorId;
        private final String content;
        private final Long createdBy;
    }
}