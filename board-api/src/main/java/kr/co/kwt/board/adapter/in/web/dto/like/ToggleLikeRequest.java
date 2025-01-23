package kr.co.kwt.board.adapter.in.web.dto.like;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "좋아요 토글 요청")
public class ToggleLikeRequest {
    @Schema(description = "사용자 ID")
    @NotNull
    private Long userId;
}