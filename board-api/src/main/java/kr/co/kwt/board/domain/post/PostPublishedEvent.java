package kr.co.kwt.board.domain.post;

import java.time.LocalDateTime;

public class PostPublishedEvent {
    private final Post post;
    private final LocalDateTime publishedAt;

    public PostPublishedEvent(Post post) {
        this.post = post;
        this.publishedAt = LocalDateTime.now();
    }
}