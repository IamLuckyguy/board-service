package kr.co.kwt.board.application.port.out.like;

import kr.co.kwt.board.domain.like.Like;

public interface SaveLikePort {
    Like save(Like like);
    void delete(Like like);
}