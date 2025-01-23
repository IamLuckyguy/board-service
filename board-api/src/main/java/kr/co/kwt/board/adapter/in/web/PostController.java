package kr.co.kwt.board.adapter.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.kwt.board.adapter.in.web.dto.post.CreatePostRequest;
import kr.co.kwt.board.adapter.in.web.dto.post.CreatePostResponse;
import kr.co.kwt.board.adapter.in.web.dto.post.UpdatePostRequest;
import kr.co.kwt.board.application.port.in.post.CreatePostUseCase;
import kr.co.kwt.board.application.port.in.post.DeletePostUseCase;
import kr.co.kwt.board.application.port.in.post.GetPostUseCase;
import kr.co.kwt.board.application.port.in.post.UpdatePostUseCase;
import kr.co.kwt.board.domain.post.PostStatus;
import kr.co.kwt.board.domain.post.PostType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@Tag(name = "게시글 API", description = "게시글 CRUD API")
@RequiredArgsConstructor
class PostController {
    private final CreatePostUseCase createPostUseCase;
    private final GetPostUseCase getPostUseCase;
    private final UpdatePostUseCase updatePostUseCase;
    private final DeletePostUseCase deletePostUseCase;

    @Operation(summary = "게시글 조회", description = "ID로 게시글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "게시글 없음")
    })
    @GetMapping("/{postId}")
    public ResponseEntity<GetPostUseCase.PostDetails> getPost(
            @Parameter(description = "게시글 ID") @PathVariable Long postId,
            @Parameter(description = "사용자 ID") @RequestHeader(required = false) Long userId) {
        return ResponseEntity.ok(getPostUseCase.getPost(postId, userId));
    }

    @Operation(summary = "게시글 목록 조회", description = "조건에 맞는 게시글 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<GetPostUseCase.PostSummary>> getPostList(
            @Parameter(description = "서비스 ID") @RequestParam(required = false) Long serviceId,
            @Parameter(description = "게시글 타입") @RequestParam(required = false) PostType postType,
            @Parameter(description = "게시글 상태") @RequestParam(required = false) PostStatus status,
            @Parameter(description = "작성자 ID") @RequestParam(required = false) Long authorId,
            @Parameter(description = "공지글 여부") @RequestParam(required = false) Boolean isPinned,
            @Parameter(description = "페이지 번호") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "20") int size) {

        GetPostUseCase.GetPostListQuery query = GetPostUseCase.GetPostListQuery.builder()
                .serviceId(serviceId)
                .postType(postType)
                .status(status)
                .authorId(authorId)
                .isPinned(isPinned != null && isPinned)
                .page(page)
                .size(size)
                .build();

        return ResponseEntity.ok(getPostUseCase.getPostList(query));
    }

    @Operation(summary = "게시글 작성", description = "새로운 게시글을 작성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "작성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping
    ResponseEntity<CreatePostResponse> createPost(
            @RequestBody @Valid CreatePostRequest request) {
        CreatePostUseCase.CreatePostCommand command = CreatePostUseCase.CreatePostCommand.builder()
                .serviceId(request.getServiceId())
                .authorId(request.getAuthorId())
                .title(request.getTitle())
                .content(request.getContent())
                .postType(request.getPostType())
                .isPinned(request.isPinned())
                .scheduledAt(request.getScheduledAt())
                .createdBy(request.getAuthorId())
                .build();

        Long postId = createPostUseCase.createPost(command);
        return ResponseEntity.ok(new CreatePostResponse(postId));
    }

    @Operation(summary = "게시글 수정", description = "기존 게시글을 수정합니다.")
    @PutMapping("/{postId}")
    public ResponseEntity<Void> updatePost(
            @Parameter(description = "게시글 ID") @PathVariable Long postId,
            @RequestBody @Valid UpdatePostRequest request) {

        UpdatePostUseCase.UpdatePostCommand command = UpdatePostUseCase.UpdatePostCommand.builder()
                .postId(postId)
                .title(request.getTitle())
                .content(request.getContent())
                .isPinned(request.isPinned())
                .scheduledAt(request.getScheduledAt())
                .updatedBy(request.getUpdatedBy())
                .build();

        updatePostUseCase.updatePost(command);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @Parameter(description = "게시글 ID") @PathVariable Long postId,
            @Parameter(description = "삭제 요청자 ID") @RequestHeader("X-User-ID") Long userId) {

        DeletePostUseCase.DeletePostCommand command = DeletePostUseCase.DeletePostCommand.builder()
                .postId(postId)
                .deletedBy(userId)
                .build();

        deletePostUseCase.deletePost(command);
        return ResponseEntity.ok().build();
    }
}