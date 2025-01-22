package kr.co.kwt.board.domain.event.comment;

import kr.co.kwt.board.domain.comment.Comment;
import kr.co.kwt.board.domain.event.DomainEvent;

import java.time.LocalDateTime;

public abstract class CommentEvent implements DomainEvent {
    protected final Comment comment;
    protected final LocalDateTime occurredAt;

    protected CommentEvent(Comment comment) {
        this.comment = comment;
        this.occurredAt = LocalDateTime.now();
    }

    @Override
    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }
}