package kr.co.kwt.board.domain.post.exception;

import kr.co.kwt.board.common.exception.BusinessException;
import kr.co.kwt.board.common.exception.ErrorCode;

public class PostDeleteException extends BusinessException {
    public PostDeleteException(String message) {
        super(ErrorCode.POST_ALREADY_DELETED, message);
    }

    public PostDeleteException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}