package kr.co.kwt.board.adapter.in.web;

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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
class CommentController {
    private final CreateCommentUseCase createCommentUseCase;
    private final UpdateCommentUseCase updateCommentUseCase;
    private final DeleteCommentUseCase deleteCommentUseCase;
    private final GetCommentUseCase getCommentUseCase;

    @PostMapping
    ResponseEntity<CreateCommentResponse> createComment(
            @RequestBody @Validated CreateCommentRequest request) {
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

    @PutMapping("/{commentId}")
    ResponseEntity<Void> updateComment(
            @PathVariable Long commentId,
            @RequestBody @Validated UpdateCommentRequest request) {
        UpdateCommentUseCase.UpdateCommentCommand command = UpdateCommentUseCase.UpdateCommentCommand.builder()
                .commentId(commentId)
                .content(request.getContent())
                .updatedBy(request.getUpdatedBy())
                .build();

        updateCommentUseCase.updateComment(command);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @RequestBody @Validated DeleteCommentRequest request) {
        DeleteCommentUseCase.DeleteCommentCommand command = DeleteCommentUseCase.DeleteCommentCommand.builder()
                .commentId(commentId)
                .deletedBy(request.getDeletedBy())
                .build();

        deleteCommentUseCase.deleteComment(command);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/posts/{postId}")
    ResponseEntity<CommentListResponse> getCommentsByPostId(
            @PathVariable Long postId,
            @RequestHeader(required = false) Long userId,
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