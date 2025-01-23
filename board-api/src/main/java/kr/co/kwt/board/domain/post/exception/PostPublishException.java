package kr.co.kwt.board.domain.post.exception;

import kr.co.kwt.board.common.exception.BusinessException;
import kr.co.kwt.board.common.exception.ErrorCode;

public class PostPublishException extends BusinessException {
    public PostPublishException(String message) {
        super(ErrorCode.POST_STATUS_CHANGE_NOT_ALLOWED, message);
    }

    public PostPublishException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}