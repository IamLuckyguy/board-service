package kr.co.kwt.board.config.resttemplate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class RestTemplateLoggingInterceptor implements ClientHttpRequestInterceptor {
    private static final AtomicLong REQUEST_ID = new AtomicLong(0);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        long requestId = REQUEST_ID.incrementAndGet();
        logRequest(requestId, request, body);

        ClientHttpResponse response = execution.execute(request, body);
        logResponse(requestId, response);

        return response;
    }

    private void logRequest(long requestId, HttpRequest request, byte[] body) {
        if (HttpMethod.GET.equals(request.getMethod())) {
            log.info("[{}] Request: {} {} with query params: {}",
                    requestId, request.getMethod(), request.getURI(),
                    request.getURI().getQuery());
        } else {
            log.info("[{}] Request: {} {}",
                    requestId, request.getMethod(), request.getURI());
        }
    }

    private void logResponse(long requestId, ClientHttpResponse response) throws IOException {
        log.info("[{}] Response Status: {}", requestId, response.getStatusCode());
    }
}