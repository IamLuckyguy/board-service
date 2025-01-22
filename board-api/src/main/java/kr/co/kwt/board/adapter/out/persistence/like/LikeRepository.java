package kr.co.kwt.board.adapter.out.persistence.like;

import kr.co.kwt.board.domain.like.LikeType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeJpaEntity, Long> {
    List<LikeJpaEntity> findByUserIdAndType(Long userId, LikeType type);
    Optional<LikeJpaEntity> findByTargetIdAndUserIdAndType(Long targetId, Long userId, LikeType type);
    long countByTargetIdAndType(Long targetId, LikeType type);
    void deleteByTargetIdAndUserIdAndType(Long targetId, Long userId, LikeType type);
}