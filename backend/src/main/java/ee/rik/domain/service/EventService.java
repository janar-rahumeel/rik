package ee.rik.domain.service;

import java.util.Set;

import ee.rik.domain.Event;
import ee.rik.domain.EventParticipant;

public interface EventService {

    Long createEvent(Event event);

    void modifyEvent(Long id, Event event);

    void deleteEvent(Long id);

    Set<EventParticipant> listAllParticipants(Long id);

}
