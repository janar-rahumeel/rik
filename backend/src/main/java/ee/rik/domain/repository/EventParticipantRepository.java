package ee.rik.domain.repository;

import java.util.Set;

import ee.rik.domain.EventParticipant;

public interface EventParticipantRepository {

    Set<EventParticipant> listAll(Long eventId);

}
