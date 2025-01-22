package kr.co.kwt.board.adapter.out.messaging.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.kwt.board.application.port.out.event.EventPublisher;
import kr.co.kwt.board.domain.event.DomainEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaEventPublisher implements EventPublisher {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void publish(DomainEvent event) {
        String topic = determineTopicName(event);
        String payload = convertToJson(event);

        kafkaTemplate.send(topic, payload)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Successfully published event: {}", event.getEventType());
                        log.debug("Offset: {}", result.getRecordMetadata().offset());
                    } else {
                        log.error("Failed to publish event: {}", ex.getMessage());
                    }
                })
                .exceptionally(throwable -> {
                    // 예외 발생 시 대체 로직
                    log.error("Exception in kafka send: ", throwable);
                    return null;
                });
    }

    private String determineTopicName(DomainEvent event) {
        return switch (event.getEventType()) {
            case "POST_CREATED" -> "board.post.created";
            case "POST_UPDATED" -> "board.post.updated";
            case "POST_DELETED" -> "board.post.deleted";
            case "COMMENT_CREATED" -> "board.comment.created";
            case "LIKE_ADDED" -> "board.like.added";
            case "LIKE_REMOVED" -> "board.like.removed";
            default -> throw new IllegalArgumentException("Unknown event type: " + event.getEventType());
        };
    }

    private String convertToJson(DomainEvent event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize event", e);
        }
    }
}