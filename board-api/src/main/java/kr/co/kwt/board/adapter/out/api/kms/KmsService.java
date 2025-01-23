package kr.co.kwt.board.adapter.out.api.kms;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import kr.co.kwt.board.adapter.out.api.kms.properties.KmsProperties;
import kr.co.kwt.board.config.resttemplate.RestTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class KmsService {

    private final KmsProperties kmsProperties;
    private final RestTemplateService restTemplateService;
    private final ObjectMapper objectMapper;
    private GetKmsResponse getKmsResponse;

    @PostConstruct
    public void init() {
        RestTemplate restTemplate = restTemplateService.getRestTemplate(
                kmsProperties.getUrl(),
                new KmsRestTemplateCustomizer(kmsProperties.getApiKey())
        );

        ResponseEntity<GetKmsResponse[]> response = restTemplate.getForEntity(
                kmsProperties.getUrl(),
                GetKmsResponse[].class
        );

        getKmsResponse = Arrays.asList(response.getBody()).get(0);
    }

    public KmsDbSecretValue getDbSecretValue() {
        return objectMapper.convertValue(getKmsResponse
                        .getSecrets()
                        .stream()
                        .filter(secret -> secret.getSecretKey().equals(kmsProperties.getSecretKey()))
                        .filter(secret -> secret.getSecretType().equals(KmsSecretType.DB.getType()))
                        .map(KmsSecret::getSecretValue)
                        .findFirst()
                        .orElseThrow(() -> new IllegalAccessError("KMS DB 정보 조회 실패, KMS 정보를 확인해주세요")),
                KmsDbSecretValue.class);
    }

    public KmsRedisSecretValue getRedisSecretValue() {
        return objectMapper.convertValue(getKmsResponse
                        .getSecrets()
                        .stream()
                        .filter(secret -> secret.getSecretKey().equals(kmsProperties.getSecretKey()))
                        .filter(secret -> secret.getSecretType().equals(KmsSecretType.REDIS.getType()))
                        .map(KmsSecret::getSecretValue)
                        .findFirst()
                        .orElseThrow(() -> new IllegalAccessError("KMS REDIS 정보 조회 실패, KMS 정보를 확인해주세요")),
                KmsRedisSecretValue.class
        );
    }
}
