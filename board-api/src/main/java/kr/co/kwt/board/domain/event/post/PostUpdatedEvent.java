package kr.co.kwt.board.domain.event.post;

import kr.co.kwt.board.domain.post.Post;

public class PostUpdatedEvent extends PostEvent {
    private final String updatedFields;

    public PostUpdatedEvent(Post post, String updatedFields) {
        super(post);
        this.updatedFields = updatedFields;
    }

    @Override
    public String getEventType() {
        return "POST_UPDATED";
    }
}