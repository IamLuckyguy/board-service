package kr.co.kwt.board.application.port.in.post;

import kr.co.kwt.board.domain.post.PostStatus;
import kr.co.kwt.board.domain.post.PostType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface SearchPostUseCase {
    Page<PostSearchResult> searchPosts(SearchPostQuery query, Pageable pageable);

    @Getter
    @Builder
    class SearchPostQuery {
        private final String keyword;
        private final Long serviceId;
        private final PostType postType;
        private final PostStatus status;
        private final SearchTarget searchTarget;
    }

    @Getter
    @Builder
    class PostSearchResult {
        private final Long id;
        private final String title;
        private final String content;
        private final PostType postType;
        private final PostStatus status;
        private final Long authorId;
        private final int viewCount;
        private final int likeCount;
        private final int commentCount;
        private final LocalDateTime createdAt;
    }

    enum SearchTarget {
        TITLE,
        CONTENT,
        TITLE_CONTENT
    }
}