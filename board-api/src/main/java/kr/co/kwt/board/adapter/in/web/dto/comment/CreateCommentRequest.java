package kr.co.kwt.board.adapter.in.web.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateCommentRequest {
    @NotNull(message = "게시물 ID는 필수입니다.")
    private Long postId;

    private Long parentId;  // 대댓글의 경우 부모 댓글 ID

    @NotNull(message = "작성자 ID는 필수입니다.")
    private Long authorId;

    @NotBlank(message = "댓글 내용은 필수입니다.")
    private String content;
}
