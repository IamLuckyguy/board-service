package kr.co.kwt.board.domain.post.exception;

import kr.co.kwt.board.common.exception.BusinessException;
import kr.co.kwt.board.common.exception.ErrorCode;

public class PostCannotBeDeletedException extends BusinessException {
    public PostCannotBeDeletedException(String message) {
        super(ErrorCode.POST_ALREADY_DELETED, message);
    }

    public PostCannotBeDeletedException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public PostCannotBeDeletedException(ErrorCode errorCode, String message, Object... args) {
        super(errorCode, message, args);
    }
}