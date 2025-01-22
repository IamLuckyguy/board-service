package kr.co.kwt.board.domain.post;

import kr.co.kwt.board.common.exception.ErrorCode;
import kr.co.kwt.board.domain.event.DomainEvent;
import kr.co.kwt.board.domain.event.post.PostCreatedEvent;
import kr.co.kwt.board.domain.event.post.PostDeletedEvent;
import kr.co.kwt.board.domain.event.post.PostUpdatedEvent;
import kr.co.kwt.board.domain.post.exception.PostCannotBePublishedException;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Post {
    private final Long id;
    private final Long serviceId;
    private final Long authorId;
    private PostContent postContent;
    private final PostType postType;
    private PostStatus status;
    private boolean isPinned;
    private int viewCount;
    private int likeCount;
    private int commentCount;
    private LocalDateTime scheduledAt;
    private LocalDateTime publishedAt;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private final Long createdBy;
    private Long updatedBy;
    private final List<DomainEvent> domainEvents;  // DomainEvent 추상 클래스를 만들면 Object 대신 사용

    @Builder
    public Post(Long id, Long serviceId, Long authorId, String title, String content,
                PostType postType, PostStatus status, boolean isPinned,
                LocalDateTime scheduledAt, Long createdBy) {
        this.id = id;
        this.serviceId = serviceId;
        this.authorId = authorId;
        this.postContent = PostContent.of(title, content);
        this.postType = postType;
        this.status = status;
        this.isPinned = isPinned;
        this.scheduledAt = scheduledAt;
        this.createdAt = LocalDateTime.now();
        this.createdBy = createdBy;
        this.updatedBy = createdBy;
        this.domainEvents = new ArrayList<>();
    }

    public void publish() {
        validateCanBePublished();
        this.status = PostStatus.PUBLISHED;
        this.publishedAt = LocalDateTime.now();
        domainEvents.add(new PostCreatedEvent(this));
    }

    public void updateContent(String title, String content, Long updatedBy) {
        validateCanBeUpdated();
        this.postContent = PostContent.of(title, content);
        this.updatedBy = updatedBy;
        this.updatedAt = LocalDateTime.now();
        domainEvents.add(new PostUpdatedEvent(this, "content"));
    }

    public void delete() {
        validateCanBeDeleted();
        this.status = PostStatus.DELETED;
        this.deletedAt = LocalDateTime.now();
        domainEvents.add(new PostDeletedEvent(this));
    }

    public List<DomainEvent> getDomainEvents() {
        return new ArrayList<>(domainEvents);
    }

    public void clearDomainEvents() {
        this.domainEvents.clear();
    }

    public void incrementViewCount() {
        if (isDeleted()) {
            throw new IllegalStateException("Cannot increment view count of deleted post");
        }
        this.viewCount++;
    }

    public void updateLikeCount(int likeCount) {
        if (isDeleted()) {
            throw new IllegalStateException("Cannot update like count of deleted post");
        }
        if (likeCount < 0) {
            throw new IllegalArgumentException("Like count cannot be negative");
        }
        this.likeCount = likeCount;
    }

    private void validateCanBePublished() {
        if (isDeleted()) {
            throw new PostCannotBePublishedException(ErrorCode.POST_NOT_FOUND, this.id);
        }

        if (this.status != PostStatus.DRAFT && this.status != PostStatus.SCHEDULED) {
            throw new PostCannotBePublishedException(
                    String.format("'%s' 상태의 게시물은 발행할 수 없습니다.", this.status));
        }

        if (this.postContent.getTitle() == null || this.postContent.getTitle().trim().isEmpty()) {
            throw new PostCannotBePublishedException("제목이 없는 게시물은 발행할 수 없습니다.");
        }

        if (this.postContent.getContent() == null || this.postContent.getContent().trim().isEmpty()) {
            throw new PostCannotBePublishedException("내용이 없는 게시물은 발행할 수 없습니다.");
        }
    }

    private void validateCanBeUpdated() {
        if (isDeleted()) {
            throw new PostCannotBeUpdatedException("삭제된 게시물은 수정할 수 없습니다.");
        }
    }

    private void validateCanBeDeleted() {
        if (isDeleted()) {
            throw new PostAlreadyDeletedException("이미 삭제된 게시물입니다.");
        }
    }

    public boolean isDeleted() {
        return this.status == PostStatus.DELETED;
    }

    public String getTitle() {
        return this.postContent.getTitle();
    }

    public String getPostContent() {
        return this.postContent.getContent();
    }

    public void schedule(LocalDateTime scheduledAt) {
        if (this.status != PostStatus.DRAFT) {
            throw new IllegalStateException("Cannot schedule post in " + this.status + " status");
        }
        this.status = PostStatus.SCHEDULED;
        this.scheduledAt = scheduledAt;
    }

    public void updatePinnedStatus(boolean pinned) {
        this.isPinned = pinned;
    }
}