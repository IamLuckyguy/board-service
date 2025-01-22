package kr.co.kwt.board.application.port.in.post;

import kr.co.kwt.board.application.port.in.dto.CommentDetails;
import kr.co.kwt.board.domain.post.PostStatus;
import kr.co.kwt.board.domain.post.PostType;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

public interface GetPostUseCase {
    PostDetails getPost(Long postId, Long userId);
    List<PostSummary> getPostList(GetPostListQuery query);

    @Getter
    @Builder
    class GetPostListQuery {
        private final Long serviceId;
        private final PostType postType;
        private final PostStatus status;
        private final Long authorId;
        private final boolean isPinned;
        private final int page;
        private final int size;
    }

    @Getter
    @Builder
    class PostDetails {
        private final Long id;
        private final Long serviceId;
        private final Long authorId;
        private final String authorName;
        private final String title;
        private final String content;
        private final PostType postType;
        private final PostStatus status;
        private final boolean isPinned;
        private final int viewCount;
        private final int likeCount;
        private final int commentCount;
        private final boolean isLiked;
        private final LocalDateTime createdAt;
        private final LocalDateTime updatedAt;
        private final List<CommentDetails> comments;
    }

    @Getter
    @Builder
    class PostSummary {
        private final Long id;
        private final String title;
        private final Long authorId;
        private final String authorName;
        private final PostType postType;
        private final int viewCount;
        private final int likeCount;
        private final int commentCount;
        private final LocalDateTime createdAt;
    }
}