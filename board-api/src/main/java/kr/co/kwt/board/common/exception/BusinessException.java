package kr.co.kwt.board.common.exception;

public class BusinessException extends BaseException {
    public BusinessException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public BusinessException(ErrorCode errorCode, String... args) {
        super(errorCode, args);
    }

    public BusinessException(ErrorCode errorCode, String message, Object... args) {
        super(errorCode, message, args);
    }
}