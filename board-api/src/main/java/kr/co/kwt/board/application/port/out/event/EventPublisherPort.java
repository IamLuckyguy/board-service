package kr.co.kwt.board.application.port.out.event;

import kr.co.kwt.board.domain.event.DomainEvent;

public interface EventPublisherPort {
    void publish(DomainEvent event);
}