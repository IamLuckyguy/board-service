package kr.co.kwt.board.domain.event.comment;

import kr.co.kwt.board.domain.comment.Comment;

public class CommentCreatedEvent extends CommentEvent {
    public CommentCreatedEvent(Comment comment) {
        super(comment);
    }

    @Override
    public String getEventType() {
        return "COMMENT_CREATED";
    }
}