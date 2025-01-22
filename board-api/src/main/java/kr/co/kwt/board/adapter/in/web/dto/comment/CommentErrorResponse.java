package kr.co.kwt.board.adapter.in.web.dto.comment;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class CommentErrorResponse {
    String code;
    String message;
    LocalDateTime timestamp;

    public static CommentErrorResponse of(String code, String message) {
        return CommentErrorResponse.builder()
                .code(code)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
}