package kr.co.kwt.board.adapter.out.api.kms;

import lombok.Getter;

import java.util.List;

@Getter
public class GetKmsResponse {
    private String serviceId;
    private String environment;
    private List<KmsSecret> secrets;
}
