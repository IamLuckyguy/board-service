package kr.co.kwt.board.adapter.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "검색 API", description = "게시글 검색 API")
@RequiredArgsConstructor
class SearchController {
    private final SearchPostUseCase searchPostUseCase;

    @Operation(summary = "게시글 검색", description = "키워드로 게시글을 검색합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "검색 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @GetMapping("/posts")
    public ResponseEntity<Page<SearchPostUseCase.PostSearchResult>> searchPosts(
            @Parameter(description = "검색 키워드", required = true) @RequestParam String keyword,
            @Parameter(description = "서비스 ID") @RequestParam(required = false) Long serviceId,
            @Parameter(description = "게시글 타입") @RequestParam(required = false) PostType postType,
            @Parameter(description = "게시글 상태") @RequestParam(required = false) PostStatus status,
            @Parameter(description = "검색 대상")
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