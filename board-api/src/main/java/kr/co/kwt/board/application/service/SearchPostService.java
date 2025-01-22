package kr.co.kwt.board.application.service;

import kr.co.kwt.board.application.port.in.post.SearchPostUseCase;
import kr.co.kwt.board.application.port.out.post.SearchPostPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SearchPostService implements SearchPostUseCase {
    private final SearchPostPort searchPostPort;

    @Override
    @Transactional(readOnly = true)
    public Page<PostSearchResult> searchPosts(SearchPostQuery query, Pageable pageable) {
        return searchPostPort.searchPosts(query, pageable);
    }
}