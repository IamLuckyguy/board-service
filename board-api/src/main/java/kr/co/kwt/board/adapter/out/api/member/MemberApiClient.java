package kr.co.kwt.board.adapter.out.api.member;

import kr.co.kwt.board.adapter.out.api.member.config.MemberApiConfig;
import kr.co.kwt.board.adapter.out.api.member.dto.MemberListResponse;
import kr.co.kwt.board.adapter.out.api.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class MemberApiClient {
    private final RestTemplate memberApiRestTemplate;
    private final MemberApiConfig memberApiConfig;

    public MemberResponse getMember(Long memberId) {
//        String url = UriComponentsBuilder
//                .fromHttpUrl(memberApiConfig.getBaseUrl())
//                .path("/api/v1/members/{memberId}")
//                .buildAndExpand(memberId)
//                .toUriString();
//
//        return memberApiRestTemplate.exchange(
//                url,
//                HttpMethod.GET,
//                new HttpEntity<>(createHeaders()),
//                MemberResponse.class
//        ).getBody();

        return MemberResponse.builder()
                .memberId(1L)
                .type("USER")
                .status("ACTIVE")
                .role("USER")
                .email("lucky@kwt.co.kr")
                .nickname("test")
                .build();
    }

    public List<MemberResponse> getMembers(Set<Long> memberIds) {
//        String url = UriComponentsBuilder
//                .fromHttpUrl(memberApiConfig.getBaseUrl())
//                .path("/api/v1/members")
//                .queryParam("memberIds", memberIds)
//                .build()
//                .toUriString();
//
//        return memberApiRestTemplate.exchange(
//                url,
//                HttpMethod.GET,
//                new HttpEntity<>(createHeaders()),
//                MemberListResponse.class
//        ).getBody().getMembers();

            return List.of(
                    MemberResponse.builder()
                            .memberId(1L)
                            .type("USER")
                            .status("ACTIVE")
                            .role("USER")
                            .email("lucky@kwt.co.kr")
                            .nickname("test")
                            .build(),
                    MemberResponse.builder()
                            .memberId(2L)
                            .type("USER")
                            .status("ACTIVE")
                            .role("USER")
                            .email("lucky2@kwt.co.kr")
                            .nickname("test2")
                            .build()
            );
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", memberApiConfig.getApiKey());
        return headers;
    }
}