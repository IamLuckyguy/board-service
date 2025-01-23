package kr.co.kwt.board.adapter.in.web.dto.like;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ToggleLikeRequest {
    @NotNull
    private Long userId;
}