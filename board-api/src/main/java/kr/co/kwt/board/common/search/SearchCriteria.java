package kr.co.kwt.board.common.search;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SearchCriteria {
    private final String key;
    private final SearchOperation operation;
    private final Object value;

    public enum SearchOperation {
        EQUALS,
        LIKE,
        IN
        // 필요한 연산 추가
    }
}