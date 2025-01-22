package kr.co.kwt.board.application.port.in.comment;

import lombok.Builder;
import lombok.Getter;

public interface UpdateCommentUseCase {
    void updateComment(UpdateCommentCommand command);

    @Getter
    @Builder
    class UpdateCommentCommand {
        private final Long commentId;
        private final String content;
        private final Long updatedBy;
    }
}