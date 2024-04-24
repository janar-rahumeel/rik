package ee.rik.domain.repository;

import ee.rik.domain.Event;

public interface EventRepository {

    Event get(Long id);

    Long create(Event event);

    void modify(Long id, Event event);

    void delete(Long id);

}
