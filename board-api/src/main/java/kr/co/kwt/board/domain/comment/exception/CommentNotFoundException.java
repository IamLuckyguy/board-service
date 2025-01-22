package kr.co.kwt.board.domain.comment.exception;

import kr.co.kwt.board.common.exception.BusinessException;
import kr.co.kwt.board.common.exception.ErrorCode;

public class CommentNotFoundException extends BusinessException {
    public CommentNotFoundException(Long commentId) {
        super(ErrorCode.COMMENT_NOT_FOUND, String.format("Comment not found with id: %d", commentId));
    }
}