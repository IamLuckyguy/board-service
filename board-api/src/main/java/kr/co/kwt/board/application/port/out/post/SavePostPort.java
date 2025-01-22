package kr.co.kwt.board.application.port.out.post;

import kr.co.kwt.board.domain.post.Post;

public interface SavePostPort {
    Post save(Post post);
}