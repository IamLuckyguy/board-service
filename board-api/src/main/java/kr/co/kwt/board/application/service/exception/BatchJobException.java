package kr.co.kwt.board.application.service.exception;

import kr.co.kwt.board.common.exception.BusinessException;
import kr.co.kwt.board.common.exception.ErrorCode;

public class BatchJobException extends BusinessException {
    public BatchJobException(String message) {
        super(ErrorCode.BATCH_JOB_FAILED, message);
    }

    public BatchJobException(String message, Throwable cause) {
        super(ErrorCode.BATCH_JOB_FAILED, message, cause);
    }

    public BatchJobException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public BatchJobException(ErrorCode errorCode, String message, Object... args) {
        super(errorCode, message, args);
    }
}