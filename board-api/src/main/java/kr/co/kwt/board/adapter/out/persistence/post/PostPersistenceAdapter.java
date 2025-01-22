package kr.co.kwt.board.adapter.out.persistence.post;

import kr.co.kwt.board.application.port.out.post.LoadPostPort;
import kr.co.kwt.board.application.port.out.post.SavePostPort;
import kr.co.kwt.board.domain.post.Post;
import kr.co.kwt.board.domain.post.PostStatus;
import kr.co.kwt.board.domain.post.PostType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class PostPersistenceAdapter implements LoadPostPort, SavePostPort {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Override
    public Post save(Post post) {
        PostJpaEntity postJpaEntity = postMapper.mapToJpaEntity(post);
        PostJpaEntity savedEntity = postRepository.save(postJpaEntity);
        return postMapper.mapToDomainEntity(savedEntity);
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id)
                .map(postMapper::mapToDomainEntity);
    }

    @Override
    public List<Post> findAllByCondition(
            Long serviceId,
            PostType postType,
            PostStatus status,
            Long authorId,
            boolean isPinned,
            int page,
            int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findAllByCondition(
                        serviceId, postType, status, authorId, isPinned, pageable)
                .stream()
                .map(postMapper::mapToDomainEntity)
                .collect(Collectors.toList());
    }
}