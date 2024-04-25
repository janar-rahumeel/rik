package ee.rik.domain.service;

import java.util.Set;

import ee.rik.domain.Event;
import ee.rik.domain.EventParticipant;
import ee.rik.domain.LegalEntityParticipant;
import ee.rik.domain.ListEvent;
import ee.rik.domain.PersonParticipant;

public interface EventService {

    Set<ListEvent> listAll(Boolean newEvents);

    Long createEvent(Event event);

    void modifyEvent(Long id, Event event);

    void deleteEvent(Long id);

    Set<EventParticipant> listAllParticipants(Long id);

    PersonParticipant addPersonParticipant(Long id, PersonParticipant personParticipant);

    LegalEntityParticipant addLegalEntityParticipant(Long id, LegalEntityParticipant legalEntityParticipant);

}
