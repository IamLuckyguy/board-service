package kr.co.kwt.board.adapter.out.api.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MemberResponse {
    private Long memberId;
    private String type;
    private String status;
    private String role;
    private String email;
    private String nickname;
}