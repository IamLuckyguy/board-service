package kr.co.kwt.board.common.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends BaseException {
    private final String resourceName;
    private final String fieldName;
    private final Object fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(ErrorCode.ENTITY_NOT_FOUND, resourceName, fieldName, String.valueOf(fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}