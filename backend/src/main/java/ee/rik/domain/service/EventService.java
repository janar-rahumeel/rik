package ee.rik.domain.service;

import ee.rik.domain.Event;

public interface EventService {

    Long createEvent(Event event);

    void modifyEvent(Long id, Event event);

    void deleteEvent(Long id);

}
