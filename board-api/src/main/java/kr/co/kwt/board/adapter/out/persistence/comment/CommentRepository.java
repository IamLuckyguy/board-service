package kr.co.kwt.board.adapter.out.persistence.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CommentRepository extends JpaRepository<CommentJpaEntity, Long> {
    @Query("SELECT c FROM CommentJpaEntity c " +
            "WHERE c.postId = :postId " +
            "ORDER BY COALESCE(c.parentId, c.id), c.createdAt")
    List<CommentJpaEntity> findByPostIdOrderByCreatedAtAsc(@Param("postId") Long postId);

    List<CommentJpaEntity> findByParentId(Long parentId);

    void deleteByPostId(Long postId);

    void deleteById(Long commentId);

    Page<CommentJpaEntity> findByPostIdAndParentIdIsNullOrderByCreatedAtDesc(Long postId, Pageable pageable);

    List<CommentJpaEntity> findByParentIdInOrderByCreatedAtAsc(List<Long> parentIds);

    @Query("SELECT COUNT(c) FROM CommentJpaEntity c WHERE c.postId = :postId AND c.deletedAt IS NULL")
    int countActiveCommentsByPostId(@Param("postId") Long postId);
}