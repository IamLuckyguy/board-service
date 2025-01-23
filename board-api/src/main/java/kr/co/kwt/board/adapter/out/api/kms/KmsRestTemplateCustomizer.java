package kr.co.kwt.board.adapter.out.api.kms;

import kr.co.kwt.board.config.resttemplate.RestTemplateCustomizer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class KmsRestTemplateCustomizer implements RestTemplateCustomizer {
    private final String KMS_HEADER = "x-api-key";
    private final String apiKey;

    @Override
    public RestTemplate customize(final RestTemplate restTemplate) {
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().set(KMS_HEADER, apiKey);
            return execution.execute(request, body);
        });
        return restTemplate;
    }
}