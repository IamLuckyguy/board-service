package kr.co.kwt.board.application.port.out.comment;

import kr.co.kwt.board.domain.comment.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface LoadCommentPort {
    Optional<Comment> findById(Long id);

    List<Comment> findByPostId(Long postId);

    List<Comment> findByParentId(Long id);

    Page<Comment> findParentCommentsByPostId(Long postId, Pageable pageable);

    List<Comment> findByParentIdIn(List<Long> parentIds);

    int countActiveCommentsByPostId(Long postId);
}