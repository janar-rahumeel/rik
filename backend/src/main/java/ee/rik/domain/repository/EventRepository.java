package ee.rik.domain.repository;

import ee.rik.domain.Event;
import ee.rik.domain.ListEvent;

import java.time.LocalDateTime;
import java.util.Set;

public interface EventRepository {

    Set<ListEvent> findAllUntil(LocalDateTime localDateTime);

    Event get(Long id);

    Long create(Event event);

    void modify(Long id, Event event);

    void delete(Long id);

}
