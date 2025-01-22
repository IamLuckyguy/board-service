package kr.co.kwt.board.application.port.in.comment;

import lombok.Builder;
import lombok.Getter;

public interface DeleteCommentUseCase {
    void deleteComment(DeleteCommentCommand command);

    @Getter
    @Builder
    class DeleteCommentCommand {
        Long commentId;
        Long deletedBy;
    }
}