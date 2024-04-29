package ee.rik.domain.repository;

import java.time.LocalDateTime;
import java.util.List;

import ee.rik.domain.Event;
import ee.rik.domain.EventListItem;

public interface EventRepository {

    List<EventListItem> getAllUntil(LocalDateTime localDateTime);

    Event get(Long id);

    Long create(Event event);

    void modify(Long id, Event event);

    void delete(Long id);

}
