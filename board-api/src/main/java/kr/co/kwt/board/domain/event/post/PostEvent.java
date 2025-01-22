package kr.co.kwt.board.domain.event.post;

import kr.co.kwt.board.domain.event.DomainEvent;
import kr.co.kwt.board.domain.post.Post;

import java.time.LocalDateTime;

public abstract class PostEvent implements DomainEvent {
    protected final Post post;
    protected final LocalDateTime occurredAt;

    protected PostEvent(Post post) {
        this.post = post;
        this.occurredAt = LocalDateTime.now();
    }

    @Override
    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }
}