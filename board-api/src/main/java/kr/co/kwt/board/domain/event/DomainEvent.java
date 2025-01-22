package kr.co.kwt.board.domain.event;

import java.time.LocalDateTime;

public interface DomainEvent {
    String getEventType();
    LocalDateTime getOccurredAt();
}