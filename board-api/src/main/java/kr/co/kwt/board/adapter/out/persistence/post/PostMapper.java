package kr.co.kwt.board.adapter.out.persistence.post;

import kr.co.kwt.board.domain.post.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public PostJpaEntity mapToJpaEntity(Post post) {
        return PostJpaEntity.builder()
                .id(post.getId())
                .serviceId(post.getServiceId())
                .authorId(post.getAuthorId())
                .title(post.getTitle())
                .content(post.getPostContent())
                .postType(post.getPostType())
                .status(post.getStatus())
                .isPinned(post.isPinned())
                .viewCount(post.getViewCount())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .deletedAt(post.getDeletedAt())
                .createdBy(post.getCreatedBy())
                .updatedBy(post.getUpdatedBy())
                .build();
    }

    public Post mapToDomainEntity(PostJpaEntity entity) {
        return Post.builder()
                .id(entity.getId())
                .serviceId(entity.getServiceId())
                .authorId(entity.getAuthorId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .postType(entity.getPostType())
                .status(entity.getStatus())
                .isPinned(entity.isPinned())
                .createdBy(entity.getCreatedBy())
                .build();
    }
}