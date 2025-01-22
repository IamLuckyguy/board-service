package kr.co.kwt.board.adapter.out.api.member.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@ConfigurationProperties(prefix = "api.member")
@Getter
@Setter
public class MemberApiConfig {
    private String baseUrl;
    private String apiKey;
    private int connectTimeout;
    private int readTimeout;

    @Bean
    public RestTemplate memberApiRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(connectTimeout);
        factory.setReadTimeout(readTimeout);

        return new RestTemplate(factory);
    }
}