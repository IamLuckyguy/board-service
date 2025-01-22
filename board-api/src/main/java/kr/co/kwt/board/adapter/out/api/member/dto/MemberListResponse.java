package kr.co.kwt.board.adapter.out.api.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MemberListResponse {
    private List<MemberResponse> members;
}
