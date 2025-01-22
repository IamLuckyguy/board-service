package kr.co.kwt.board.application.port.in.post;

import lombok.Builder;
import lombok.Getter;

public interface DeletePostUseCase {
    void deletePost(DeletePostCommand command);

    @Getter
    @Builder
    class DeletePostCommand {
        private final Long postId;
        private final Long deletedBy;
    }
}