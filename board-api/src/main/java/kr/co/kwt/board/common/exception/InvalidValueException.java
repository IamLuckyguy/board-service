package kr.co.kwt.board.common.exception;

public class InvalidValueException extends BusinessException {
    public InvalidValueException(String message) {
        super(ErrorCode.INVALID_INPUT_VALUE, message);
    }
}