package kr.co.kwt.board.domain.post.exception;

import kr.co.kwt.board.common.exception.ResourceNotFoundException;

public class PostNotFoundException extends ResourceNotFoundException {
    public PostNotFoundException(Long id) {
        super("Post", "id", id);
    }
}