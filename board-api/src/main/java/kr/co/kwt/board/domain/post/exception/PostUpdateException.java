package kr.co.kwt.board.domain.post.exception;

import kr.co.kwt.board.common.exception.BusinessException;
import kr.co.kwt.board.common.exception.ErrorCode;

public class PostUpdateException extends BusinessException {
    public PostUpdateException(String message) { super(ErrorCode.POST_UPDATE_NOT_ALLOWED, message); }

    public PostUpdateException(ErrorCode errorCode, String message) { super(errorCode, message); }
}