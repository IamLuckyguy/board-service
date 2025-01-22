package kr.co.kwt.board.adapter.out.persistence.comment;

import kr.co.kwt.board.application.port.out.comment.DeleteCommentPort;
import kr.co.kwt.board.application.port.out.comment.LoadCommentPort;
import kr.co.kwt.board.application.port.out.comment.SaveCommentPort;
import kr.co.kwt.board.domain.comment.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class CommentPersistenceAdapter implements SaveCommentPort, LoadCommentPort, DeleteCommentPort {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public Comment save(Comment comment) {
        CommentJpaEntity commentJpaEntity = commentMapper.mapToJpaEntity(comment);
        CommentJpaEntity savedEntity = commentRepository.save(commentJpaEntity);
        return commentMapper.mapToDomainEntity(savedEntity);
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id)
                .map(commentMapper::mapToDomainEntity);
    }

    @Override
    public List<Comment> findByPostId(Long postId) {
        List<CommentJpaEntity> comments = commentRepository.findByPostIdOrderByCreatedAtAsc(postId);
        return comments.stream()
                .map(commentMapper::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Comment> findByParentId(Long id) {
        return commentRepository.findByParentId(id).stream()
                .map(commentMapper::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Comment> findParentCommentsByPostId(Long postId, Pageable pageable) {
        Page<CommentJpaEntity> commentEntities = commentRepository.findByPostIdAndParentIdIsNullOrderByCreatedAtDesc(postId, pageable);
        return commentEntities.map(commentMapper::mapToDomainEntity);
    }

    @Override
    public List<Comment> findByParentIdIn(List<Long> parentIds) {
        return commentRepository.findByParentIdInOrderByCreatedAtAsc(parentIds)
                .stream()
                .map(commentMapper::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public int countActiveCommentsByPostId(Long postId) {
        return commentRepository.countActiveCommentsByPostId(postId);
    }

    @Override
    public void delete(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public void deleteByPostId(Long postId) {
        commentRepository.deleteByPostId(postId);
    }
}