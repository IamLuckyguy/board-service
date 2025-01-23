package kr.co.kwt.board.common.exception;

public class BusinessException extends BaseException {
    public BusinessException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BusinessException(ErrorCode errorCode, String... args) {
        super(errorCode, args);
    }
}