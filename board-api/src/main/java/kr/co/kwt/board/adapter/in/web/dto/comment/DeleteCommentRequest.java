package kr.co.kwt.board.adapter.in.web.dto.comment;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeleteCommentRequest {
    @NotNull(message = "삭제자 ID는 필수입니다.")
    private Long deletedBy;
}