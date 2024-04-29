package ee.rik.domain.service;

import java.util.List;
import java.util.Set;

import ee.rik.domain.Event;
import ee.rik.domain.EventListItem;
import ee.rik.domain.EventParticipant;
import ee.rik.domain.LegalEntityParticipant;
import ee.rik.domain.PersonParticipant;

public interface EventService {

    List<EventListItem> getAllEvents(Boolean newEvents);

    Event getEvent(Long id);

    Long createEvent(Event event);

    void modifyEvent(Long id, Event event);

    void deleteEvent(Long id);

    List<EventParticipant> listAllParticipants(Long id);

    PersonParticipant addPersonParticipant(Long id, PersonParticipant personParticipant);

    LegalEntityParticipant addLegalEntityParticipant(Long id, LegalEntityParticipant legalEntityParticipant);

}
