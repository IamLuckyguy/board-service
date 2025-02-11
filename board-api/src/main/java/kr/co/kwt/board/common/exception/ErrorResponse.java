package kr.co.kwt.board.common.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "API 에러 응답")
public class ErrorResponse {
    @Schema(description = "에러 발생 시간")
    private LocalDateTime timestamp;

    @Schema(description = "에러 코드")
    private String code;

    @Schema(description = "에러 메시지")
    private String message;

    @Schema(description = "상세 에러 정보")
    private List<FieldError> errors;

    @Schema(description = "에러 발생 경로")
    @Setter
    private String path;

    private ErrorResponse(ErrorCode code, String message) {
        this.timestamp = LocalDateTime.now();
        this.code = code.getCode();
        this.message = message;
        this.errors = new ArrayList<>();
    }

    private ErrorResponse(ErrorCode code, String message, String path) {
        this(code, message);
        this.path = path;
    }

    public static ErrorResponse of(ErrorCode code, String message) {
        return new ErrorResponse(code, message);
    }

    public static ErrorResponse of(ErrorCode code, BindingResult bindingResult) {
        return new ErrorResponse(code, code.getMessage(), bindingResult);
    }

    public static ErrorResponse of(MethodArgumentTypeMismatchException e) {
        String value = e.getValue() == null ? "" : e.getValue().toString();
        List<ErrorResponse.FieldError> errors = ErrorResponse.FieldError.of(
                e.getName(), value, e.getErrorCode());
        return new ErrorResponse(ErrorCode.INVALID_TYPE_VALUE, e.getMessage(), errors);
    }

    private ErrorResponse(ErrorCode code, String message, BindingResult bindingResult) {
        this.timestamp = LocalDateTime.now();
        this.code = code.getCode();
        this.message = message;
        this.errors = FieldError.of(bindingResult);
    }

    private ErrorResponse(ErrorCode code, String message, List<FieldError> errors) {
        this.timestamp = LocalDateTime.now();
        this.code = code.getCode();
        this.message = message;
        this.errors = errors;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        private FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldError> of(String field, String value, String reason) {
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, value, reason));
            return fieldErrors;
        }

        private static List<FieldError> of(BindingResult bindingResult) {
            return bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }
}