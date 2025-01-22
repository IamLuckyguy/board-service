package kr.co.kwt.board.domain.comment.exception;

import kr.co.kwt.board.common.exception.BusinessException;
import kr.co.kwt.board.common.exception.ErrorCode;

public class CommentAlreadyDeletedException extends BusinessException {
    public CommentAlreadyDeletedException(Long commentId) {
        super(ErrorCode.COMMENT_ALREADY_DELETED, String.format("Comment already deleted with id: %d", commentId));
    }
}