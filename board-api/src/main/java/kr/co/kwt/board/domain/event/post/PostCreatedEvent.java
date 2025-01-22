package kr.co.kwt.board.domain.event.post;

import kr.co.kwt.board.domain.post.Post;

public class PostCreatedEvent extends PostEvent {
    public PostCreatedEvent(Post post) {
        super(post);
    }

    @Override
    public String getEventType() {
        return "POST_CREATED";
    }
}