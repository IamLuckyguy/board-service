package kr.co.kwt.board.adapter.in.web.dto.post;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.co.kwt.board.domain.post.PostType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CreatePostRequest {
    @NotNull
    private Long serviceId;

    @NotNull
    private Long authorId;

    @NotEmpty
    @Size(max = 255)
    private String title;

    @NotEmpty
    private String content;

    @NotNull
    private PostType postType;

    private boolean isPinned;

    private LocalDateTime scheduledAt;
}