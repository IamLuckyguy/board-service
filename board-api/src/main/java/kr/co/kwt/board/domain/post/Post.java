package kr.co.kwt.board.domain.post;

import kr.co.kwt.board.common.exception.ErrorCode;
import kr.co.kwt.board.domain.event.DomainEvent;
import kr.co.kwt.board.domain.event.post.PostCreatedEvent;
import kr.co.kwt.board.domain.event.post.PostDeletedEvent;
import kr.co.kwt.board.domain.event.post.PostUpdatedEvent;
import kr.co.kwt.board.domain.post.exception.PostDeleteException;
import kr.co.kwt.board.domain.post.exception.PostNotFoundException;
import kr.co.kwt.board.domain.post.exception.PostPublishException;
import kr.co.kwt.board.domain.post.exception.PostUpdateException;
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
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private final Long createdBy;
    private Long updatedBy;
    private final List<DomainEvent> domainEvents;

    @Builder
    public Post(Long id, Long serviceId, Long authorId, String title, String content,
                PostType postType, PostStatus status, boolean isPinned, Long createdBy) {
        this.id = id;
        this.serviceId = serviceId;
        this.authorId = authorId;
        this.postContent = PostContent.of(title, content);
        this.postType = postType;
        this.status = status;
        this.isPinned = isPinned;
        this.createdAt = LocalDateTime.now();
        this.createdBy = createdBy;
        this.updatedBy = createdBy;
        this.domainEvents = new ArrayList<>();
    }

    public void publish() {
        validatePublish();
        this.status = PostStatus.ACTIVE;
        domainEvents.add(new PostCreatedEvent(this));
    }

    public void updateContent(String title, String content, Long updatedBy) {
        validateUpdate(updatedBy);
        this.postContent = PostContent.of(title, content);
        this.updatedBy = updatedBy;
        this.updatedAt = LocalDateTime.now();
        domainEvents.add(new PostUpdatedEvent(this, "content"));
    }

    public void delete() {
        validateDelete();
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

    public void incrementCommentCount() {
        this.commentCount++;
    }

    public void decrementCommentCount() {
        if (this.commentCount > 0) {
            this.commentCount--;
        }
    }

    public void updateLikeCount(int likeCount) {
        if (isDeleted()) {
            throw new PostNotFoundException(this.id);
        }
        if (likeCount < 0) {
            throw new PostUpdateException(ErrorCode.POST_UPDATE_NOT_ALLOWED, "좋아요 수는 음수가 될 수 없습니다.");
        }
        this.likeCount = likeCount;
    }

    private void validatePublish() {

    }

    private void validateUpdate(Long updatedBy) {
        if (isDeleted()) {
            throw new PostUpdateException(
                    ErrorCode.POST_NOT_FOUND,
                    String.format(ErrorCode.POST_NOT_FOUND.getMessage() + " : %d", this.id)
            );
        }

        if (!this.authorId.equals(updatedBy)) {
            throw new PostUpdateException("게시글은 작성자만 수정할 수 있습니다.");
        }
    }

    private void validateDelete() {
        if (isDeleted()) {
            throw new PostDeleteException("이미 삭제된 게시물입니다.");
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

    public void updatePinnedStatus(boolean pinned) {
        this.isPinned = pinned;
    }

    public void syncCommentCount(int actualCommentCount) {
        if (this.commentCount != actualCommentCount) {
            this.commentCount = actualCommentCount;
//            domainEvents.add(new CommentCountSyncedEvent(this));  // 필요한 경우
        }
    }

    public List<DomainEvent> publishAndClearEvents() {
        List<DomainEvent> events = getDomainEvents();
        clearDomainEvents();
        return events;
    }
}