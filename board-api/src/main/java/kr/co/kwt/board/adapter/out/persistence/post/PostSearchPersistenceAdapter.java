package kr.co.kwt.board.adapter.out.persistence.post;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kr.co.kwt.board.application.port.in.post.SearchPostUseCase;
import kr.co.kwt.board.application.port.out.post.SearchPostPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
class PostSearchPersistenceAdapter implements SearchPostPort {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public Page<SearchPostUseCase.PostSearchResult> searchPosts(SearchPostUseCase.SearchPostQuery query, Pageable pageable) {
        // 동적 쿼리 생성
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT NEW kr.co.kwt.board.application.port.in.search.SearchPostUseCase$PostSearchResult(");
        jpql.append("p.id, p.title, p.content, p.postType, p.status, p.authorId, ");
        jpql.append("p.viewCount, p.likeCount, p.commentCount, p.createdAt) ");
        jpql.append("FROM PostJpaEntity p ");
        jpql.append("WHERE 1=1 ");

        // 파라미터 값 저장
        List<Object> parameters = new ArrayList<>();
        int parameterIndex = 0;

        // 검색어 조건
        if (query.getKeyword() != null && !query.getKeyword().trim().isEmpty()) {
            switch (query.getSearchTarget()) {
                case TITLE:
                    jpql.append("AND MATCH(p.title) AGAINST (?").append(++parameterIndex).append(" IN BOOLEAN MODE) ");
                    parameters.add(query.getKeyword());
                    break;
                case CONTENT:
                    jpql.append("AND MATCH(p.content) AGAINST (?").append(++parameterIndex).append(" IN BOOLEAN MODE) ");
                    parameters.add(query.getKeyword());
                    break;
                case TITLE_CONTENT:
                    jpql.append("AND (MATCH(p.title, p.content) AGAINST (?").append(++parameterIndex).append(" IN BOOLEAN MODE)) ");
                    parameters.add(query.getKeyword());
                    break;
            }
        }

        // 서비스 ID 조건
        if (query.getServiceId() != null) {
            jpql.append("AND p.serviceId = ?").append(++parameterIndex).append(" ");
            parameters.add(query.getServiceId());
        }

        // 게시물 타입 조건
        if (query.getPostType() != null) {
            jpql.append("AND p.postType = ?").append(++parameterIndex).append(" ");
            parameters.add(query.getPostType());
        }

        // 상태 조건
        if (query.getStatus() != null) {
            jpql.append("AND p.status = ?").append(++parameterIndex).append(" ");
            parameters.add(query.getStatus());
        }

        // 전체 카운트 조회
        String countJpql = jpql.toString().replace(
                "NEW kr.co.kwt.board.application.port.in.search.SearchPostUseCase$PostSearchResult(" +
                        "p.id, p.title, p.content, p.postType, p.status, p.authorId, " +
                        "p.viewCount, p.likeCount, p.commentCount, p.createdAt)",
                "COUNT(p)"
        );

        var query1 = em.createQuery(countJpql);
        var query2 = em.createQuery(jpql.toString());

        // 파라미터 바인딩
        for (int i = 0; i < parameters.size(); i++) {
            query1.setParameter(i + 1, parameters.get(i));
            query2.setParameter(i + 1, parameters.get(i));
        }

        // 페이징 처리
        query2.setFirstResult((int) pageable.getOffset());
        query2.setMaxResults(pageable.getPageSize());

        long total = ((Number) query1.getSingleResult()).longValue();
        List<SearchPostUseCase.PostSearchResult> results = query2.getResultList();

        return new PageImpl<>(results, pageable, total);
    }
}