package kr.co.kwt.board.application.port.in.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class CommentDetails {
    private final Long id;
    private final Long authorId;
    private final String authorName;
    private final String authorProfileUrl;
    private final String content;
    private final int likeCount;
    private final boolean isLiked;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final List<CommentDetails> replies;
}