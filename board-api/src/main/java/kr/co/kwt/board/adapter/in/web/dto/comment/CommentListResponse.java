package kr.co.kwt.board.adapter.in.web.dto.comment;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class CommentListResponse {
    List<CommentResponse> comments;
    PageInfo pageInfo;

    @Value
    @Builder
    public static class PageInfo {
        int currentPage;
        int totalPages;
        long totalElements;
        int size;
    }
}