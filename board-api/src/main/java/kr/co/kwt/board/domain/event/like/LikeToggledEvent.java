package kr.co.kwt.board.domain.event.like;

import kr.co.kwt.board.domain.like.Like;

public class LikeToggledEvent extends LikeEvent {
    private final boolean isLiked;

    public LikeToggledEvent(Like like, boolean isLiked) {
        super(like);
        this.isLiked = isLiked;
    }

    @Override
    public String getEventType() {
        return isLiked ? "LIKE_ADDED" : "LIKE_REMOVED";
    }
}