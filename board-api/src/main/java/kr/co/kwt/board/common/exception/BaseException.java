package kr.co.kwt.board.common.exception;

import lombok.Getter;

import java.util.Arrays;

@Getter
public abstract class BaseException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String[] args;

    protected BaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.args = new String[]{};
    }

    protected BaseException(ErrorCode errorCode, String message) {
        super(String.format(errorCode.getMessage() + " : %s", message));
        this.errorCode = errorCode;
        this.args = new String[]{};
    }

    protected BaseException(ErrorCode errorCode, String... args) {
        super(String.format(errorCode.getMessage(), (Object[]) args));
        this.errorCode = errorCode;
        this.args = args;
    }

    protected BaseException(ErrorCode errorCode, String message, Object... args) {
        super(String.format(errorCode.getMessage(), message, (Object[]) args));
        this.errorCode = errorCode;
        this.args = new String[]{Arrays.toString(args)};
    }

    protected BaseException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
        this.args = new String[]{};
    }
}
