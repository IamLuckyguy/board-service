package kr.co.kwt.board.application.service;

import kr.co.kwt.board.application.port.in.comment.CreateCommentUseCase;
import kr.co.kwt.board.application.port.in.comment.DeleteCommentUseCase;
import kr.co.kwt.board.application.port.in.comment.GetCommentUseCase;
import kr.co.kwt.board.application.port.in.dto.CommentDetails;
import kr.co.kwt.board.application.port.out.comment.DeleteCommentPort;
import kr.co.kwt.board.application.port.out.comment.LoadCommentPort;
import kr.co.kwt.board.application.port.out.comment.SaveCommentPort;
import kr.co.kwt.board.application.port.in.comment.UpdateCommentUseCase;
import kr.co.kwt.board.application.port.out.post.LoadPostPort;
import kr.co.kwt.board.application.port.out.post.SavePostPort;
import kr.co.kwt.board.domain.comment.Comment;
import kr.co.kwt.board.domain.post.Post;
import kr.co.kwt.board.domain.comment.exception.CommentNotFoundException;
import kr.co.kwt.board.domain.post.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CommentService implements
        CreateCommentUseCase,
        UpdateCommentUseCase,
        DeleteCommentUseCase,
        GetCommentUseCase {

    private final LoadCommentPort loadCommentPort;
    private final SaveCommentPort saveCommentPort;
    private final DeleteCommentPort deleteCommentPort;
    private final LoadPostPort loadPostPort;
    private final SavePostPort savePostPort;
    private final AuthorService authorService;

    @Override
    @Transactional
    public Long createComment(CreateCommentCommand command) {
        // 게시물 존재 여부 확인
        Post post = loadPostPort.findById(command.getPostId())
                .orElseThrow(() -> new PostNotFoundException(command.getPostId()));

        Comment comment = Comment.builder()
                .postId(command.getPostId())
                .parentId(command.getParentId())
                .authorId(command.getAuthorId())
                .content(command.getContent())
                .createdBy(command.getCreatedBy()
        ).build();

        Comment savedComment = saveCommentPort.save(comment);

        // 게시물의 댓글 수 증가
        post.incrementCommentCount();
        savePostPort.save(post);

        return savedComment.getId();
    }

    @Override
    @Transactional
    public void updateComment(UpdateCommentCommand command) {
        Comment comment = loadCommentPort.findById(command.getCommentId())
                .orElseThrow(() -> new CommentNotFoundException(command.getCommentId()));

        comment.updateContent(command.getContent(), command.getUpdatedBy());
        saveCommentPort.save(comment);
    }

    @Override
    @Transactional
    public void deleteComment(DeleteCommentCommand command) {
        Comment comment = loadCommentPort.findById(command.getCommentId())
                .orElseThrow(() -> new CommentNotFoundException(command.getCommentId()));

        // 게시물 조회
        Post post = loadPostPort.findById(comment.getPostId())
                .orElseThrow(() -> new PostNotFoundException(comment.getPostId()));

        // 댓글이 이미 삭제되었는지 확인
        if (!comment.isDeleted()) {
            comment.delete(command.getDeletedBy());
            saveCommentPort.save(comment);

            // 연관된 답글들도 삭제
            List<Comment> replies = loadCommentPort.findByParentId(comment.getId());
            for (Comment reply : replies) {
                if (!reply.isDeleted()) {
                    reply.delete(command.getDeletedBy());
                    saveCommentPort.save(reply);

                    // 답글당 게시물의 댓글 수 감소
                    post.decrementCommentCount();
                }
            }

            // 원댓글에 대한 게시물의 댓글 수 감소
            post.decrementCommentCount();
            savePostPort.save(post);
        }
    }

    @Override
    public CommentDetails getComment(Long commentId) {
        Comment comment = loadCommentPort.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));

        return mapToCommentDetails(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommentDetails> getCommentsByPostId(Long postId, Long userId, Pageable pageable) {
        // 1. 부모 댓글만 페이징해서 조회
        Page<Comment> parentComments = loadCommentPort.findParentCommentsByPostId(postId, pageable);

        if (parentComments.isEmpty()) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        // 2. 조회된 부모 댓글의 모든 자식 댓글을 한 번에 조회
        List<Long> parentIds = parentComments.getContent().stream()
                .map(Comment::getId)
                .collect(Collectors.toList());
        List<Comment> childComments = loadCommentPort.findByParentIdIn(parentIds);

        // 3. 모든 댓글의 작성자 정보를 한 번에 조회
        Set<Long> authorIds = Stream.concat(
                        parentComments.getContent().stream(),
                        childComments.stream()
                ).map(Comment::getAuthorId)
                .collect(Collectors.toSet());

        Map<Long, String> authorNames = authorService.getAuthorNames(authorIds);

        // 4. 자식 댓글을 부모 ID별로 그룹화
        Map<Long, List<Comment>> commentsByParentId = childComments.stream()
                .collect(Collectors.groupingBy(Comment::getParentId));

        // 5. 페이징된 부모 댓글에 대해 계층 구조 구성
        List<CommentDetails> commentDetails = parentComments.getContent().stream()
                .map(comment -> buildCommentDetails(comment, commentsByParentId, authorNames))
                .collect(Collectors.toList());

        return new PageImpl<>(
                commentDetails,
                pageable,
                parentComments.getTotalElements()
        );
    }

    private List<CommentDetails> buildCommentHierarchy(List<Comment> comments, Map<Long, String> authorNames) {
        Map<Long, List<Comment>> commentsByParentId = comments.stream()
                .filter(comment -> comment.getParentId() != null)
                .collect(Collectors.groupingBy(Comment::getParentId));

        return comments.stream()
                .filter(comment -> comment.getParentId() == null)
                .map(comment -> buildCommentDetails(comment, commentsByParentId, authorNames))
                .collect(Collectors.toList());
    }

    private CommentDetails buildCommentDetails(
            Comment comment,
            Map<Long, List<Comment>> commentsByParentId,
            Map<Long, String> authorNames) {

        List<CommentDetails> replies = commentsByParentId
                .getOrDefault(comment.getId(), Collections.emptyList())
                .stream()
                .map(reply -> buildCommentDetails(reply, commentsByParentId, authorNames))
                .collect(Collectors.toList());

        return CommentDetails.builder()
                .id(comment.getId())
                .authorId(comment.getAuthorId())
                .authorName(authorNames.getOrDefault(comment.getAuthorId(), "Unknown"))
                .content(comment.getContent())
                .likeCount(comment.getLikeCount())
                .createdAt(comment.getCreatedAt())
                .replies(replies)
                .build();
    }

    private CommentDetails mapToCommentDetails(Comment comment) {
        List<Comment> replies = loadCommentPort.findByParentId(comment.getId());

        return CommentDetails.builder()
                .id(comment.getId())
                .authorId(comment.getAuthorId())
                .content(comment.getContent())
                .likeCount(comment.getLikeCount())
                .createdAt(comment.getCreatedAt())
                .replies(replies.stream()
                        .map(this::mapToCommentDetails)
                        .collect(Collectors.toList()))
                .build();
    }
}