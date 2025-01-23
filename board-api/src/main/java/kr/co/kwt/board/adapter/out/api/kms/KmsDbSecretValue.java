package kr.co.kwt.board.adapter.out.api.kms;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class KmsDbSecretValue {

    private String driver;
    private String host;
    private Integer port;
    private String database;
    private String username;
    private String password;

    public String getJdbcUrl() {
        return "jdbc:mysql://" + host + ":" + port + "/" + database;
    }
}