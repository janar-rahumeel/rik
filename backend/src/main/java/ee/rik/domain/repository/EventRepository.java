package ee.rik.domain.repository;

import java.time.LocalDateTime;
import java.util.Set;

import ee.rik.domain.Event;
import ee.rik.domain.EventListItem;

public interface EventRepository {

    Set<EventListItem> getAllUntil(LocalDateTime localDateTime);

    Event get(Long id);

    Long create(Event event);

    void modify(Long id, Event event);

    void delete(Long id);

}
