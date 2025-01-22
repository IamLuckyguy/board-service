package kr.co.kwt.board.adapter.out.persistence.post;

import jakarta.persistence.*;
import kr.co.kwt.board.domain.post.PostStatus;
import kr.co.kwt.board.domain.post.PostType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class PostJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "author_id")
    private Long authorId;

    private String title;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_type")
    private PostType postType;

    @Enumerated(EnumType.STRING)
    private PostStatus status;

    @Column(name = "is_pinned")
    private boolean isPinned;

    @Column(name = "view_count")
    private int viewCount;

    @Column(name = "like_count")
    private int likeCount;

    @Column(name = "comment_count")
    private int commentCount;

    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

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
    public PostJpaEntity(Long id, Long serviceId, Long authorId, String title, String content, PostType postType,
                         PostStatus status, boolean isPinned, int viewCount, int likeCount, int commentCount,
                         LocalDateTime scheduledAt, LocalDateTime publishedAt, LocalDateTime createdAt,
                         LocalDateTime updatedAt, LocalDateTime deletedAt, Long createdBy, Long updatedBy) {
        this.id = id;
        this.serviceId = serviceId;
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.postType = postType;
        this.status = status;
        this.isPinned = isPinned;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.scheduledAt = scheduledAt;
        this.publishedAt = publishedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public void incrementViewCount() {
        this.viewCount++;
    }

    public void updateLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public void updateContent(String title, String content, Long updatedBy) {
        this.title = title;
        this.content = content;
        this.updatedBy = updatedBy;
        this.updatedAt = LocalDateTime.now();
    }
}