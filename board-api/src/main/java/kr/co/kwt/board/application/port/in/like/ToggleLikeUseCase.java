package kr.co.kwt.board.application.port.in.like;

import kr.co.kwt.board.domain.like.LikeType;
import lombok.Builder;
import lombok.Getter;

public interface ToggleLikeUseCase {
    boolean toggleLike(ToggleLikeCommand command);

    @Getter
    @Builder
    class ToggleLikeCommand {
        private final Long targetId;
        private final Long userId;
        private final LikeType type;
    }
}
