package kr.co.kwt.board.domain.event.post;

import kr.co.kwt.board.domain.post.Post;

public class PostDeletedEvent extends PostEvent {
    public PostDeletedEvent(Post post) {
        super(post);
    }

    @Override
    public String getEventType() {
        return "POST_DELETED";
    }
}