package kr.co.kwt.board.adapter.in.web;

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
@RequiredArgsConstructor
class PostController {
    private final CreatePostUseCase createPostUseCase;
    private final GetPostUseCase getPostUseCase;
    private final UpdatePostUseCase updatePostUseCase;
    private final DeletePostUseCase deletePostUseCase;


    @GetMapping("/{postId}")
    public ResponseEntity<GetPostUseCase.PostDetails> getPost(
            @PathVariable Long postId,
            @RequestHeader(required = false) Long userId) {
        return ResponseEntity.ok(getPostUseCase.getPost(postId, userId));
    }

    @GetMapping
    public ResponseEntity<List<GetPostUseCase.PostSummary>> getPostList(
            @RequestParam(required = false) Long serviceId,
            @RequestParam(required = false) PostType postType,
            @RequestParam(required = false) PostStatus status,
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) Boolean isPinned,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

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

    @PostMapping
    ResponseEntity<CreatePostResponse> createPost(@RequestBody CreatePostRequest request) {
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

    @PutMapping("/{postId}")
    public ResponseEntity<Void> updatePost(
            @PathVariable Long postId,
            @RequestBody UpdatePostRequest request) {

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

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            @RequestHeader("X-User-ID") Long userId) {

        DeletePostUseCase.DeletePostCommand command = DeletePostUseCase.DeletePostCommand.builder()
                .postId(postId)
                .deletedBy(userId)
                .build();

        deletePostUseCase.deletePost(command);
        return ResponseEntity.ok().build();
    }
}