package kr.co.kwt.board.adapter.out.persistence.comment;

import kr.co.kwt.board.domain.comment.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public CommentJpaEntity mapToJpaEntity(Comment comment) {
        return CommentJpaEntity.builder()
                .id(comment.getId())
                .postId(comment.getPostId())
                .parentId(comment.getParentId())
                .authorId(comment.getAuthorId())
                .content(comment.getContent())
                .createdBy(comment.getCreatedBy())
                .build();
    }

    public Comment mapToDomainEntity(CommentJpaEntity entity) {
        return Comment.builder()
                .id(entity.getId())
                .postId(entity.getPostId())
                .parentId(entity.getParentId())
                .authorId(entity.getAuthorId())
                .content(entity.getContent())
                .createdBy(entity.getCreatedBy())
                .build();
    }
}