package kr.co.kwt.board.application.port.in.comment;

import kr.co.kwt.board.application.port.in.dto.CommentDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetCommentUseCase {
    CommentDetails getComment(Long commentId);
    Page<CommentDetails> getCommentsByPostId(Long postId, Long userId, Pageable pageable);
}