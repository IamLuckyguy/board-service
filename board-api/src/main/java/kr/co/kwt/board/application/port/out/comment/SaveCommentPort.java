package kr.co.kwt.board.application.port.out.comment;

import kr.co.kwt.board.domain.comment.Comment;

public interface SaveCommentPort {
    Comment save(Comment comment);
}