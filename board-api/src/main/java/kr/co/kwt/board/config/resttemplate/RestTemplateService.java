package kr.co.kwt.board.config.resttemplate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class RestTemplateService {
    private final Map<String, RestTemplate> restTemplateCache = new ConcurrentHashMap<>();

    public RestTemplate getRestTemplate(String baseUrl) {
        return restTemplateCache.computeIfAbsent(baseUrl, this::createRestTemplate);
    }

    public RestTemplate getRestTemplate(String baseUrl, RestTemplateCustomizer customizer) {
        String cacheKey = baseUrl + customizer.getClass().getName();
        return restTemplateCache.computeIfAbsent(cacheKey,
                key -> customizer.customize(createRestTemplate(baseUrl)));
    }

    private RestTemplate createRestTemplate(String baseUrl) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(new RestTemplateLoggingInterceptor()));
        return restTemplate;
    }

    public void clearCache() {
        restTemplateCache.clear();
    }

    public void removeRestTemplate(String baseUrl) {
        restTemplateCache.remove(baseUrl);
    }
}