package kr.co.kwt.board.domain.post.exception;

import kr.co.kwt.board.common.exception.BusinessException;
import kr.co.kwt.board.common.exception.ErrorCode;

public class PostNotFoundException extends BusinessException {
    public PostNotFoundException(String message) {
        super(ErrorCode.POST_NOT_FOUND, message);
    }

    public PostNotFoundException(Long postId) {
        super(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage() + " id : " + postId);
    }

    public PostNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public PostNotFoundException(ErrorCode errorCode, String message, Object... args) {
        super(errorCode, message, args);
    }
}