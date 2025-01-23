package kr.co.kwt.board.domain.comment.exception;

import kr.co.kwt.board.common.exception.ResourceNotFoundException;

public class CommentNotFoundException extends ResourceNotFoundException {
    public CommentNotFoundException(Long id) {
        super("Comment", "id", id);
    }
}