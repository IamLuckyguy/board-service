package kr.co.kwt.board.domain.post.exception;

import kr.co.kwt.board.common.exception.BusinessException;
import kr.co.kwt.board.common.exception.ErrorCode;

public class PostCannotBePublishedException extends BusinessException {
    public PostCannotBePublishedException(String message) {
        super(ErrorCode.POST_STATUS_CHANGE_NOT_ALLOWED, message);
    }

    public PostCannotBePublishedException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    // 부모 클래스의 다른 생성자들도 필요한 경우 오버로딩
    public PostCannotBePublishedException(ErrorCode errorCode, String message, Object... args) {
        super(errorCode, message, args);
    }
}