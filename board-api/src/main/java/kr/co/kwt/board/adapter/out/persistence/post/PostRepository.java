package kr.co.kwt.board.adapter.out.persistence.post;

import kr.co.kwt.board.domain.post.PostStatus;
import kr.co.kwt.board.domain.post.PostType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<PostJpaEntity, Long> {
    @Query("SELECT p FROM PostJpaEntity p WHERE " +
            "(:serviceId IS NULL OR p.serviceId = :serviceId) AND " +
            "(:postType IS NULL OR p.postType = :postType) AND " +
            "(:status IS NULL OR p.status = :status) AND " +
            "(:authorId IS NULL OR p.authorId = :authorId) AND " +
            "(:isPinned IS NULL OR p.isPinned = :isPinned) " +
            "ORDER BY p.isPinned DESC, p.createdAt DESC")
    List<PostJpaEntity> findAllByCondition(
            @Param("serviceId") Long serviceId,
            @Param("postType") PostType postType,
            @Param("status") PostStatus status,
            @Param("authorId") Long authorId,
            @Param("isPinned") Boolean isPinned,
            Pageable pageable
    );
}