package kr.co.kwt.board.adapter.out.persistence.comment;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class CommentJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "author_id")
    private Long authorId;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "like_count")
    private int likeCount;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Builder
    public CommentJpaEntity(Long id, Long postId, Long parentId, Long authorId,
                            String content, int likeCount, LocalDateTime createdAt,
                            LocalDateTime updatedAt, LocalDateTime deletedAt,
                            Long createdBy, Long updatedBy) {
        this.id = id;
        this.postId = postId;
        this.parentId = parentId;
        this.authorId = authorId;
        this.content = content;
        this.likeCount = likeCount;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public void updateContent(String content, Long updatedBy) {
        this.content = content;
        this.updatedBy = updatedBy;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }
}