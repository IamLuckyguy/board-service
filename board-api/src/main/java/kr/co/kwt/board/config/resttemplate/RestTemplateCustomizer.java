package kr.co.kwt.board.config.resttemplate;

import org.springframework.web.client.RestTemplate;

public interface RestTemplateCustomizer {
    RestTemplate customize(RestTemplate restTemplate);
}