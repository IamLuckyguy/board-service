package kr.co.kwt.board.application.service;

import kr.co.kwt.board.application.port.in.dto.CommentDetails;
import kr.co.kwt.board.application.port.in.post.CreatePostUseCase;
import kr.co.kwt.board.application.port.in.post.DeletePostUseCase;
import kr.co.kwt.board.application.port.in.post.GetPostUseCase;
import kr.co.kwt.board.application.port.in.post.UpdatePostUseCase;
import kr.co.kwt.board.application.port.out.comment.LoadCommentPort;
import kr.co.kwt.board.application.port.out.event.EventPublisherPort;
import kr.co.kwt.board.application.port.out.like.LoadLikePort;
import kr.co.kwt.board.application.port.out.post.LoadPostPort;
import kr.co.kwt.board.application.port.out.post.SavePostPort;
import kr.co.kwt.board.domain.comment.Comment;
import kr.co.kwt.board.domain.like.Like;
import kr.co.kwt.board.domain.like.LikeType;
import kr.co.kwt.board.domain.post.Post;
import kr.co.kwt.board.domain.post.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService implements CreatePostUseCase, UpdatePostUseCase, DeletePostUseCase, GetPostUseCase {
    private final SavePostPort savePostPort;
    private final LoadPostPort loadPostPort;
    private final LoadCommentPort loadCommentPort;
    private final LoadLikePort loadLikePort;
    private final EventPublisherPort eventPublisherPort;
    private final AuthorService authorService; // 작성자 정보 조회 서비스

    @Override
    @Transactional
    public PostDetails getPost(Long postId, Long userId) {
        Post post = loadPostPort.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        // 조회수 증가
        post.incrementViewCount();
        savePostPort.save(post);

        // 댓글 조회 및 계층 구조 구성
        List<Comment> comments = loadCommentPort.findByPostId(postId);
        List<CommentDetails> commentDetails = buildCommentHierarchy(comments, userId);

        // 좋아요 상태 확인
        boolean isLiked = userId != null &&
                loadLikePort.findByTargetIdAndUserIdAndType(postId, userId, LikeType.POST).isPresent();

        // 작성자 정보 조회
        String authorName = authorService.getAuthorName(post.getAuthorId());

        return PostDetails.builder()
                .id(post.getId())
                .serviceId(post.getServiceId())
                .authorId(post.getAuthorId())
                .authorName(authorName)
                .title(post.getTitle())
                .content(post.getPostContent())
                .postType(post.getPostType())
                .status(post.getStatus())
                .isPinned(post.isPinned())
                .viewCount(post.getViewCount())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .isLiked(isLiked)
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .comments(commentDetails)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostSummary> getPostList(GetPostListQuery query) {
        List<Post> posts = loadPostPort.findAllByCondition(
                query.getServiceId(),
                query.getPostType(),
                query.getStatus(),
                query.getAuthorId(),
                query.isPinned(),
                query.getPage(),
                query.getSize()
        );

        // 작성자 정보 일괄 조회
        Set<Long> authorIds = posts.stream()
                .map(Post::getAuthorId)
                .collect(Collectors.toSet());
        Map<Long, String> authorNames = authorService.getAuthorNames(authorIds);

        return posts.stream()
                .map(post -> PostSummary.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .authorId(post.getAuthorId())
                        .authorName(authorNames.get(post.getAuthorId()))
                        .postType(post.getPostType())
                        .viewCount(post.getViewCount())
                        .likeCount(post.getLikeCount())
                        .commentCount(post.getCommentCount())
                        .createdAt(post.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    private List<CommentDetails> buildCommentHierarchy(List<Comment> comments, Long userId) {
        Map<Long, List<Comment>> commentsByParentId = comments.stream()
                .filter(comment -> comment.getParentId() != null)
                .collect(Collectors.groupingBy(Comment::getParentId));

        // 작성자 정보 일괄 조회
        Set<Long> authorIds = comments.stream()
                .map(Comment::getAuthorId)
                .collect(Collectors.toSet());
        Map<Long, String> authorNames = authorService.getAuthorNames(authorIds);

        // 댓글의 좋아요 상태 일괄 조회
        Map<Long, Boolean> commentLikes = new HashMap<>();
        if (userId != null) {
            Set<Long> likedCommentIds = loadLikePort.findByUserIdAndType(userId, LikeType.COMMENT)
                    .stream()
                    .map(Like::getTargetId)
                    .collect(Collectors.toSet());

            comments.forEach(comment ->
                    commentLikes.put(comment.getId(), likedCommentIds.contains(comment.getId())));
        }

        return comments.stream()
                .filter(comment -> comment.getParentId() == null)
                .map(comment -> buildCommentDetails(comment, commentsByParentId, authorNames, commentLikes))
                .collect(Collectors.toList());
    }

    private CommentDetails buildCommentDetails(
            Comment comment,
            Map<Long, List<Comment>> commentsByParentId,
            Map<Long, String> authorNames,
            Map<Long, Boolean> commentLikes) {

        List<CommentDetails> replies = commentsByParentId
                .getOrDefault(comment.getId(), Collections.emptyList())
                .stream()
                .map(reply -> buildCommentDetails(reply, commentsByParentId, authorNames, commentLikes))
                .collect(Collectors.toList());

        return CommentDetails.builder()
                .id(comment.getId())
                .authorId(comment.getAuthorId())
                .authorName(authorNames.getOrDefault(comment.getAuthorId(), "Unknown"))
                .authorProfileUrl("")  // TODO: 프로필 이미지 URL 로직 추가 필요
                .content(comment.getContent())
                .likeCount(comment.getLikeCount())
                .isLiked(commentLikes.getOrDefault(comment.getId(), false))
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .replies(replies)
                .build();
    }

    @Override
    @Transactional
    public Long createPost(CreatePostCommand command) {
        Post post = Post.builder()
                .serviceId(command.getServiceId())
                .authorId(command.getAuthorId())
                .title(command.getTitle())
                .content(command.getContent())
                .postType(command.getPostType())
                .isPinned(command.isPinned())
                .createdBy(command.getCreatedBy())
                .build();

        post.publish();
        Post savedPost = savePostPort.save(post);
        publishEvents(post);
        return savedPost.getId();
    }

    @Override
    @Transactional
    public void updatePost(UpdatePostCommand command) {
        Post post = loadPostPort.findById(command.getPostId())
                .orElseThrow(() -> new PostNotFoundException(command.getPostId()));

        // 게시글 제목, 내용, 수정자 정보 업데이트
        post.updateContent(
                command.getTitle(),
                command.getContent(),
                command.getUpdatedBy()
        );

        // Update pinned status if changed
        post.updatePinnedStatus(command.isPinned());

        savePostPort.save(post);
        publishEvents(post);
    }

    @Override
    @Transactional
    public void deletePost(DeletePostCommand command) {
        Post post = loadPostPort.findById(command.getPostId())
                .orElseThrow(() -> new PostNotFoundException(command.getPostId()));

        post.delete();
        savePostPort.save(post);
        publishEvents(post);
    }

    private void publishEvents(Post post) {
        post.publishAndClearEvents().forEach(eventPublisherPort::publish);
    }
}