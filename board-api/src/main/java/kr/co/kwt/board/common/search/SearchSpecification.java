package kr.co.kwt.board.common.search;

import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class SearchSpecification<T> implements Specification<T> {
    private final List<SearchCriteria> criteriaList;

    public SearchSpecification() {
        this.criteriaList = new ArrayList<>();
    }

    public void add(SearchCriteria criteria) {
        criteriaList.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        for (SearchCriteria criteria : criteriaList) {
            switch (criteria.getOperation()) {
                case EQUALS:
                    predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
                    break;
                case LIKE:
                    predicates.add(builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%"));
                    break;
                case IN:
                    predicates.add(root.get(criteria.getKey()).in(criteria.getValue()));
                    break;
                // 필요한 연산 추가
            }
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
