package kr.co.kwt.board.adapter.in.web;

import kr.co.kwt.board.application.port.in.post.SearchPostUseCase;
import kr.co.kwt.board.domain.post.PostStatus;
import kr.co.kwt.board.domain.post.PostType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
class SearchController {
    private final SearchPostUseCase searchPostUseCase;

    @GetMapping("/posts")
    public ResponseEntity<Page<SearchPostUseCase.PostSearchResult>> searchPosts(
            @RequestParam String keyword,
            @RequestParam(required = false) Long serviceId,
            @RequestParam(required = false) PostType postType,
            @RequestParam(required = false) PostStatus status,
            @RequestParam(required = false, defaultValue = "TITLE_CONTENT") SearchPostUseCase.SearchTarget searchTarget,
            Pageable pageable) {

        SearchPostUseCase.SearchPostQuery query = SearchPostUseCase.SearchPostQuery.builder()
                .keyword(keyword)
                .serviceId(serviceId)
                .postType(postType)
                .status(status)
                .searchTarget(searchTarget)
                .build();

        return ResponseEntity.ok(searchPostUseCase.searchPosts(query, pageable));
    }
}