package kr.co.kwt.board.adapter.out.persistence.like;

import kr.co.kwt.board.domain.like.Like;
import org.springframework.stereotype.Component;

@Component
public class LikeMapper {
    public LikeJpaEntity mapToJpaEntity(Like like) {
        return LikeJpaEntity.builder()
                .id(like.getId())
                .targetId(like.getTargetId())
                .userId(like.getUserId())
                .type(like.getType())
                .build();
    }

    public Like mapToDomainEntity(LikeJpaEntity entity) {
        return Like.builder()
                .id(entity.getId())
                .targetId(entity.getTargetId())
                .userId(entity.getUserId())
                .type(entity.getType())
                .build();
    }
}