package kr.co.kwt.board.adapter.in.web;

import kr.co.kwt.board.adapter.in.web.dto.like.LikeResponse;
import kr.co.kwt.board.adapter.in.web.dto.like.ToggleLikeRequest;
import kr.co.kwt.board.application.port.in.like.ToggleLikeUseCase;
import kr.co.kwt.board.domain.like.LikeType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/likes")
@RequiredArgsConstructor
class LikeController {
    private final ToggleLikeUseCase toggleLikeUseCase;

    @PostMapping("/posts/{postId}")
    public ResponseEntity<LikeResponse> togglePostLike(
            @PathVariable Long postId,
            @RequestBody ToggleLikeRequest request) {
        boolean isLiked = toggleLikeUseCase.toggleLike(
                ToggleLikeUseCase.ToggleLikeCommand.builder()
                        .targetId(postId)
                        .userId(request.getUserId())
                        .type(LikeType.POST)
                        .build()
        );
        return ResponseEntity.ok(new LikeResponse(isLiked));
    }

    @PostMapping("/comments/{commentId}")
    public ResponseEntity<LikeResponse> toggleCommentLike(
            @PathVariable Long commentId,
            @RequestBody ToggleLikeRequest request) {
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