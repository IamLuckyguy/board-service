package kr.co.kwt.board.domain.post.exception;

import kr.co.kwt.board.common.exception.BusinessException;
import kr.co.kwt.board.common.exception.ErrorCode;

public class PostNotFoundException extends BusinessException {
    public PostNotFoundException(Long postId) {
        super(ErrorCode.POST_NOT_FOUND, String.format("Post not found with id: %d", postId));
    }
}