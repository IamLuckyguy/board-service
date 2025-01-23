package kr.co.kwt.board.domain.comment.exception;

import kr.co.kwt.board.common.exception.BusinessException;
import kr.co.kwt.board.common.exception.ErrorCode;

public class CommentUpdateException extends BusinessException {
    public CommentUpdateException(Long commentId) {
        super(ErrorCode.COMMENT_UPDATE_NOT_ALLOWED, String.format("Comment update not allowed with id: %d", commentId));
    }
}
