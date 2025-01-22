package kr.co.kwt.board.application.port.out.post;

import kr.co.kwt.board.application.port.in.post.SearchPostUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchPostPort {
    Page<SearchPostUseCase.PostSearchResult> searchPosts(SearchPostUseCase.SearchPostQuery query, Pageable pageable);
}