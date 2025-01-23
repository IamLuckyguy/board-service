package kr.co.kwt.board.adapter.out.api.kms;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
class KmsResponse {
    private List<GetKmsResponse> responses;
}