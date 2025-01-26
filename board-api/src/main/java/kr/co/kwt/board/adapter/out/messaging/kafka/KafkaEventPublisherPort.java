package kr.co.kwt.board.adapter.out.messaging.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.kwt.board.application.port.out.event.EventPublisherPort;
import kr.co.kwt.board.domain.event.DomainEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaEventPublisherPort implements EventPublisherPort {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final KafkaTopicProperties topicProperties;

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
                    log.error("Exception in kafka send: ", throwable);
                    return null;
                });
    }

    private String determineTopicName(DomainEvent event) {
        return switch (event.getEventType()) {
            case "POST_CREATED" -> topicProperties.getPost().getCreated();
            case "POST_UPDATED" -> topicProperties.getPost().getUpdated();
            case "POST_DELETED" -> topicProperties.getPost().getDeleted();
            case "COMMENT_CREATED" -> topicProperties.getComment().getCreated();
            case "LIKE_ADDED" -> topicProperties.getLike().getAdded();
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