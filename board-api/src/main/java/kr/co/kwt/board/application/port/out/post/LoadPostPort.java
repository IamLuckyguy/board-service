package kr.co.kwt.board.application.port.out.post;

import kr.co.kwt.board.domain.post.Post;
import kr.co.kwt.board.domain.post.PostStatus;
import kr.co.kwt.board.domain.post.PostType;

import java.util.List;
import java.util.Optional;

public interface LoadPostPort {
    Optional<Post> findById(Long id);
    List<Post> findAllByCondition(
            Long serviceId,
            PostType postType,
            PostStatus status,
            Long authorId,
            boolean isPinned,
            int page,
            int size
    );
}