package kr.co.kwt.board.adapter.in.web.dto.post;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UpdatePostRequest {
    @NotEmpty
    @Size(max = 255)
    private String title;

    @NotEmpty
    private String content;

    private boolean isPinned;

    private LocalDateTime scheduledAt;

    @NotNull
    private Long updatedBy;
}