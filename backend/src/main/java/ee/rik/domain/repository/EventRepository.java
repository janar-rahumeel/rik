package ee.rik.domain.repository;

import java.time.LocalDateTime;
import java.util.Set;

import ee.rik.domain.Event;
import ee.rik.domain.ListEvent;

public interface EventRepository {

    Set<ListEvent> findAllUntil(LocalDateTime localDateTime);

    Event get(Long id);

    Long create(Event event);

    void modify(Long id, Event event);

    void delete(Long id);

}
