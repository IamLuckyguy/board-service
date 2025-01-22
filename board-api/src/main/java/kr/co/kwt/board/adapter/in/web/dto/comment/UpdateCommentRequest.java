package kr.co.kwt.board.adapter.in.web.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateCommentRequest {
    @NotBlank(message = "댓글 내용은 필수입니다.")
    private String content;

    @NotNull(message = "수정자 ID는 필수입니다.")
    private Long updatedBy;
}