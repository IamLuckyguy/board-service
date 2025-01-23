package kr.co.kwt.board.application.port.out.like;

import kr.co.kwt.board.domain.like.Like;
import kr.co.kwt.board.domain.like.LikeType;

import java.util.Optional;

public interface LoadLikePort {
    Optional<Like> findByTargetIdAndUserIdAndType(Long targetId, Long userId, LikeType type);

    long countByTargetIdAndType(Long targetId, LikeType type);

    Optional<Like> findByUserIdAndType(Long userId, LikeType likeType);
}