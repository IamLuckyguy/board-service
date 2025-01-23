package kr.co.kwt.board.adapter.out.persistence.like;

import kr.co.kwt.board.application.port.out.like.LoadLikePort;
import kr.co.kwt.board.application.port.out.like.SaveLikePort;
import kr.co.kwt.board.domain.like.Like;
import kr.co.kwt.board.domain.like.LikeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class LikePersistenceAdapter implements SaveLikePort, LoadLikePort {
    private final LikeRepository likeRepository;
    private final LikeMapper likeMapper;

    @Override
    public Like save(Like like) {
        LikeJpaEntity likeJpaEntity = likeMapper.mapToJpaEntity(like);
        LikeJpaEntity savedEntity = likeRepository.save(likeJpaEntity);
        return likeMapper.mapToDomainEntity(savedEntity);
    }

    @Override
    public void delete(Like like) {
        likeRepository.deleteById(like.getId());
    }

    @Override
    public Optional<Like> findByTargetIdAndUserIdAndType(Long targetId, Long userId, LikeType type) {
        return likeRepository.findByTargetIdAndUserIdAndType(targetId, userId, type)
                .map(likeMapper::mapToDomainEntity);
    }

    @Override
    public long countByTargetIdAndType(Long targetId, LikeType type) {
        return likeRepository.countByTargetIdAndType(targetId, type);
    }

    @Override
    public Optional<Like> findByUserIdAndType(Long userId, LikeType likeType) {
        return likeRepository.findByUserIdAndType(userId, likeType)
                .stream()
                .map(likeMapper::mapToDomainEntity)
                .findFirst();
    }
}