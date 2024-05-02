package ee.rik.domain.service;

import java.util.List;

import ee.rik.domain.Event;
import ee.rik.domain.EventListItem;
import ee.rik.domain.EventParticipant;

public interface EventService {

    List<EventListItem> getAllEvents(Boolean newEvents);

    Event getEvent(Long id);

    Long createEvent(Event event);

    void modifyEvent(Long id, Event event);

    void deleteEvent(Long id);

    List<EventParticipant> listAllParticipants(Long id);

}
