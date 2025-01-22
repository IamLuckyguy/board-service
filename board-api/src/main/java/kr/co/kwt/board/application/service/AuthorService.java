package kr.co.kwt.board.application.service;

import kr.co.kwt.board.adapter.in.web.dto.comment.CommentResponse;
import kr.co.kwt.board.adapter.out.api.member.MemberApiClient;
import kr.co.kwt.board.adapter.out.api.member.dto.MemberResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final MemberApiClient memberApiClient;

    @Cacheable(value = "authorNames", key = "#authorId")
    public String getAuthorName(Long authorId) {
        return memberApiClient.getMember(authorId).getNickname();
    }

    @Cacheable(value = "authorNames", key = "#authorIds")
    public Map<Long, String> getAuthorNames(Set<Long> authorIds) {
        return memberApiClient.getMembers(authorIds).stream()
                .collect(Collectors.toMap(
                        MemberResponse::getMemberId,
                        MemberResponse::getNickname
                ));
    }
}