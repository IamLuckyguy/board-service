package kr.co.kwt.board.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 공통 에러
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "입력값이 올바르지 않습니다"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C002", "허용되지 않는 메소드입니다"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C003", "내부 서버 오류가 발생했습니다"),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "C004", "타입이 올바르지 않습니다"),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "C005", "엔티티를 찾을 수 없습니다"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "C006", "접근이 거부되었습니다"),

    // 게시글 관련 에러
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "게시글을 찾을 수 없습니다"),
    POST_ALREADY_DELETED(HttpStatus.NOT_FOUND, "P002", "게시글이 이미 삭제되었습니다"),
    POST_STATUS_CHANGE_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "P003", "게시글 상태 변경을 허용하지 않습니다"),
    POST_UPDATE_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "P004", "게시글 수정을 허용하지 않습니다"),

    // 댓글 관련 에러
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "M001", "댓글을 찾을 수 없습니다"),
    COMMENT_ALREADY_DELETED(HttpStatus.NOT_FOUND, "M002", "댓글이 이미 삭제되었습니다"),
    COMMENT_UPDATE_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "M003", "댓글 수정을 허용하지 않습니다"),

    // 좋아요 관련 에러
    LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "L001", "좋아요를 찾을 수 없습니다"),
    LIKE_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "L002", "이미 좋아요를 누르셨습니다"),

    // 배치 작업 관련 에러
    BATCH_JOB_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "B001", "배치 작업 실행 중 오류가 발생했습니다"),
    BATCH_JOB_INVALID_STATE(HttpStatus.BAD_REQUEST, "B002", "배치 작업의 상태가 올바르지 않습니다");

    private final HttpStatus status;
    private final String code;
    private final String message;
}