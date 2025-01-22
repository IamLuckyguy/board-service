package kr.co.kwt.board.common.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode, String message, Object... args) {
        super(String.format(message, args));
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode, String message, Map<String, Object> params) {
        super(formatMessage(message, params));
        this.errorCode = errorCode;
    }

    protected static String formatMessage(String message, Map<String, Object> params) {
        if (params == null || params.isEmpty()) {
            return message;
        }

        String formattedMessage = message;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            formattedMessage = formattedMessage.replace(
                    "{" + entry.getKey() + "}",
                    String.valueOf(entry.getValue())
            );
        }
        return formattedMessage;
    }
}