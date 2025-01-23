package kr.co.kwt.board.domain.comment;

import kr.co.kwt.board.domain.comment.exception.CommentDeleteException;
import kr.co.kwt.board.domain.comment.exception.CommentUpdateException;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Comment {
    private final Long id;
    private final Long postId;
    private final Long parentId;
    private final Long authorId;
    private String content;
    private int likeCount;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private final Long createdBy;
    private Long updatedBy;
    private final List<Comment> replies;

    @Builder
    public Comment(Long id, Long postId, Long parentId, Long authorId, String content,
                   Long createdBy) {
        this.id = id;
        this.postId = postId;
        this.parentId = parentId;
        this.authorId = authorId;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.createdBy = createdBy;
        this.updatedBy = createdBy;
        this.replies = new ArrayList<>();
    }

    public void updateContent(String content, Long updatedBy) {
        if (this.deletedAt != null) {
            throw new CommentUpdateException(this.id);
        }
        this.content = content;
        this.updatedBy = updatedBy;
        this.updatedAt = LocalDateTime.now();
    }

    public void delete(Long deletedBy) {
        if (this.deletedAt != null) {
            throw new CommentDeleteException(this.id);
        }
        this.deletedAt = LocalDateTime.now();
        this.updatedBy = deletedBy;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isDeleted() {
        return this.deletedAt != null;
    }

    public void updateLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public void addReply(Comment reply) {
        if (this.parentId != null) {
            throw new IllegalStateException("Nested comments are only allowed for one level");
        }
        this.replies.add(reply);
    }
}