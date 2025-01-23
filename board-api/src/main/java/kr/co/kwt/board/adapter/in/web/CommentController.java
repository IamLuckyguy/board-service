package kr.co.kwt.board.adapter.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.kwt.board.adapter.in.web.dto.comment.*;
import kr.co.kwt.board.application.port.in.comment.CreateCommentUseCase;
import kr.co.kwt.board.application.port.in.comment.DeleteCommentUseCase;
import kr.co.kwt.board.application.port.in.comment.GetCommentUseCase;
import kr.co.kwt.board.application.port.in.comment.UpdateCommentUseCase;
import kr.co.kwt.board.application.port.in.dto.CommentDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/comments")
@Tag(name = "댓글 API", description = "댓글 CRUD API")
@RequiredArgsConstructor
class CommentController {
    private final CreateCommentUseCase createCommentUseCase;
    private final UpdateCommentUseCase updateCommentUseCase;
    private final DeleteCommentUseCase deleteCommentUseCase;
    private final GetCommentUseCase getCommentUseCase;

    @Operation(summary = "댓글 작성", description = "새로운 댓글을 작성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "작성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping
    ResponseEntity<CreateCommentResponse> createComment(
            @RequestBody @Valid CreateCommentRequest request) {
        CreateCommentUseCase.CreateCommentCommand command = CreateCommentUseCase.CreateCommentCommand.builder()
                .postId(request.getPostId())
                .parentId(request.getParentId())
                .authorId(request.getAuthorId())
                .content(request.getContent())
                .createdBy(request.getAuthorId())
                .build();

        Long commentId = createCommentUseCase.createComment(command);

        CreateCommentResponse response = CreateCommentResponse.builder()
                .commentId(commentId)
                .createdAt(LocalDateTime.now())
                .build();

        return ResponseEntity
                .created(URI.create("/api/v1/comments/" + commentId))
                .body(response);
    }

    @Operation(summary = "댓글 수정", description = "기존 댓글을 수정합니다.")
    @PutMapping("/{commentId}")
    ResponseEntity<Void> updateComment(
            @Parameter(description = "댓글 ID") @PathVariable Long commentId,
            @RequestBody @Valid UpdateCommentRequest request) {
        UpdateCommentUseCase.UpdateCommentCommand command = UpdateCommentUseCase.UpdateCommentCommand.builder()
                .commentId(commentId)
                .content(request.getContent())
                .updatedBy(request.getUpdatedBy())
                .build();

        updateCommentUseCase.updateComment(command);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    @DeleteMapping("/{commentId}")
    ResponseEntity<Void> deleteComment(
            @Parameter(description = "댓글 ID") @PathVariable Long commentId,
            @RequestBody @Valid DeleteCommentRequest request) {
        DeleteCommentUseCase.DeleteCommentCommand command = DeleteCommentUseCase.DeleteCommentCommand.builder()
                .commentId(commentId)
                .deletedBy(request.getDeletedBy())
                .build();

        deleteCommentUseCase.deleteComment(command);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "게시글별 댓글 목록 조회", description = "특정 게시글의 댓글 목록을 조회합니다.")
    @GetMapping("/posts/{postId}")
    ResponseEntity<CommentListResponse> getCommentsByPostId(
            @Parameter(description = "게시글 ID") @PathVariable Long postId,
            @Parameter(description = "사용자 ID") @RequestHeader(required = false) Long userId,
            Pageable pageable) {
        Page<CommentDetails> commentPage =
                getCommentUseCase.getCommentsByPostId(postId, userId, pageable);

        CommentListResponse response = CommentListResponse.builder()
                .comments(mapToCommentResponses(commentPage.getContent()))
                .pageInfo(CommentListResponse.PageInfo.builder()
                        .currentPage(pageable.getPageNumber())
                        .totalPages(commentPage.getTotalPages())
                        .totalElements(commentPage.getTotalElements())
                        .size(pageable.getPageSize())
                        .build())
                .build();

        return ResponseEntity.ok(response);
    }

    private List<CommentResponse> mapToCommentResponses(
            List<CommentDetails> comments) {
        return comments.stream()
                .map(this::mapToCommentResponse)
                .collect(Collectors.toList());
    }

    private CommentResponse mapToCommentResponse(
            CommentDetails comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .author(CommentResponse.AuthorInfo.builder()
                        .id(comment.getAuthorId())
                        .name(comment.getAuthorName())
                        .profileImageUrl(comment.getAuthorProfileUrl())
                        .build())
                .content(comment.getContent())
                .stats(CommentResponse.Stats.builder()
                        .likeCount(comment.getLikeCount())
                        .isLiked(comment.isLiked())
                        .build())
                .auditInfo(CommentResponse.AuditInfo.builder()
                        .createdAt(comment.getCreatedAt())
                        .updatedAt(comment.getUpdatedAt())
                        .build())
                .replies(mapToCommentResponses(comment.getReplies()))
                .build();
    }
}