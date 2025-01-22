package kr.co.kwt.board.adapter.in.web.dto.comment;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class CommentResponse {
    Long id;
    AuthorInfo author;
    String content;
    Stats stats;
    AuditInfo auditInfo;
    List<CommentResponse> replies;

    @Value
    @Builder
    public static class AuthorInfo {
        Long id;
        String name;
        String profileImageUrl;
    }

    @Value
    @Builder
    public static class Stats {
        int likeCount;
        boolean isLiked;  // 현재 사용자의 좋아요 여부
    }

    @Value
    @Builder
    public static class AuditInfo {
        LocalDateTime createdAt;
        LocalDateTime updatedAt;
    }
}