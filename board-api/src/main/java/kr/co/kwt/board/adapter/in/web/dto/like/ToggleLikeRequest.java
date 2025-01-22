package kr.co.kwt.board.adapter.in.web.dto.like;

import lombok.Builder;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Builder
public class ToggleLikeRequest {
    @NotNull
    private Long userId;
}