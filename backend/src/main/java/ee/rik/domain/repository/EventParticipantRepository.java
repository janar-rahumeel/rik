package ee.rik.domain.repository;

import java.util.Set;

import ee.rik.domain.EventParticipant;

public interface EventParticipantRepository {

    Set<EventParticipant> listAll(Long eventId);

    boolean legalEntityParticipantExists(Long id, Long legalEntityParticipantId);

    void addLegalEntityParticipant(Long eventId, Long legalEntityParticipantId);

    void removeLegalEntityParticipant(Long eventId, Long legalEntityParticipantId);

}
