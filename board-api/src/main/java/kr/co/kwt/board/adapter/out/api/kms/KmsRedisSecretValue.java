package kr.co.kwt.board.adapter.out.api.kms;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class KmsRedisSecretValue {

    private String host;
    private Integer port;
    private String master;
    private List<String> nodes;
    private String password;
}
