package kr.co.kwt.board.common.exception;

public class InvalidRequestException extends BaseException {
    public InvalidRequestException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidRequestException(ErrorCode errorCode, String... args) {
        super(errorCode, args);
    }
}