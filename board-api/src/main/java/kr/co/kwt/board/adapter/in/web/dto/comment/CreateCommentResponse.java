package kr.co.kwt.board.adapter.in.web.dto.comment;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class CreateCommentResponse {
    Long commentId;
    LocalDateTime createdAt;
}