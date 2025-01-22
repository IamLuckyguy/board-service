package kr.co.kwt.board.domain.like;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class Like {
    private final Long id;
    private final Long targetId;  // postId 또는 commentId
    private final Long userId;
    private final LikeType type;
    private final LocalDateTime createdAt;

    @Builder
    public Like(Long id, Long targetId, Long userId, LikeType type) {
        this.id = id;
        this.targetId = targetId;
        this.userId = userId;
        this.type = type;
        this.createdAt = LocalDateTime.now();
    }
}