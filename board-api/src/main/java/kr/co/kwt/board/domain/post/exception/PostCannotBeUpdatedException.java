package kr.co.kwt.board.domain.post.exception;

import kr.co.kwt.board.common.exception.BusinessException;
import kr.co.kwt.board.common.exception.ErrorCode;

public class PostCannotBeUpdatedException extends BusinessException {
    public PostCannotBeUpdatedException(String message) {
        super(ErrorCode.POST_UPDATE_NOT_ALLOWED, message);
    }

    public PostCannotBeUpdatedException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public PostCannotBeUpdatedException(ErrorCode errorCode, String message, Object... args) {
        super(errorCode, message, args);
    }
}