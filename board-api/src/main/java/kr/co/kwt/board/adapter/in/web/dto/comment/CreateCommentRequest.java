package kr.co.kwt.board.adapter.in.web.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "댓글 생성 요청")
public class CreateCommentRequest {
    @Schema(description = "게시글 ID")
    @NotNull(message = "게시물 ID는 필수입니다.")
    private Long postId;

    @Schema(description = "부모 댓글 ID")
    private Long parentId;  // 대댓글의 경우 부모 댓글 ID

    @Schema(description = "작성자 ID")
    @NotNull(message = "작성자 ID는 필수입니다.")
    private Long authorId;

    @Schema(description = "댓글 내용")
    @NotBlank(message = "댓글 내용은 필수입니다.")
    private String content;
}
