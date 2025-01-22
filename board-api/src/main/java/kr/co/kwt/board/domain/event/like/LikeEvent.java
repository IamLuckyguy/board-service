package kr.co.kwt.board.domain.event.like;

import kr.co.kwt.board.domain.event.DomainEvent;
import kr.co.kwt.board.domain.like.Like;

import java.time.LocalDateTime;

public abstract class LikeEvent implements DomainEvent {
    protected final Like like;
    protected final LocalDateTime occurredAt;

    protected LikeEvent(Like like) {
        this.like = like;
        this.occurredAt = LocalDateTime.now();
    }

    @Override
    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }
}