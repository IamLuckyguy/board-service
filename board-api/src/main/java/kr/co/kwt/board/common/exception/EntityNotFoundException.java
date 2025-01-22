package kr.co.kwt.board.common.exception;

public class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException(String message) {
        super(ErrorCode.ENTITY_NOT_FOUND, message);
    }
}