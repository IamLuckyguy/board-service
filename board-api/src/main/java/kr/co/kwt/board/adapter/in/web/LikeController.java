package kr.co.kwt.board.adapter.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.kwt.board.adapter.in.web.dto.like.LikeResponse;
import kr.co.kwt.board.adapter.in.web.dto.like.ToggleLikeRequest;
import kr.co.kwt.board.application.port.in.like.ToggleLikeUseCase;
import kr.co.kwt.board.domain.like.LikeType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/likes")
@Tag(name = "좋아요 API", description = "게시글/댓글 좋아요 API")
@RequiredArgsConstructor
class LikeController {
    private final ToggleLikeUseCase toggleLikeUseCase;

    @Operation(summary = "게시글 좋아요 토글", description = "게시글의 좋아요를 추가하거나 취소합니다.")
    @PostMapping("/posts/{postId}")
    public ResponseEntity<LikeResponse> togglePostLike(
            @Parameter(description = "게시글 ID") @PathVariable Long postId,
            @RequestBody @Valid ToggleLikeRequest request) {
        boolean isLiked = toggleLikeUseCase.toggleLike(
                ToggleLikeUseCase.ToggleLikeCommand.builder()
                        .targetId(postId)
                        .userId(request.getUserId())
                        .type(LikeType.POST)
                        .build()
        );
        return ResponseEntity.ok(new LikeResponse(isLiked));
    }

    @Operation(summary = "댓글 좋아요 토글", description = "댓글의 좋아요를 추가하거나 취소합니다.")
    @PostMapping("/comments/{commentId}")
    public ResponseEntity<LikeResponse> toggleCommentLike(
            @Parameter(description = "댓글 ID")  @PathVariable Long commentId,
            @RequestBody @Valid ToggleLikeRequest request) {
        boolean isLiked = toggleLikeUseCase.toggleLike(
                ToggleLikeUseCase.ToggleLikeCommand.builder()
                        .targetId(commentId)
                        .userId(request.getUserId())
                        .type(LikeType.COMMENT)
                        .build()
        );
        return ResponseEntity.ok(new LikeResponse(isLiked));
    }
}