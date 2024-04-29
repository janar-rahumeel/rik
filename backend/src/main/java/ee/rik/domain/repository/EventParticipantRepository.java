package ee.rik.domain.repository;

import java.util.List;

import ee.rik.domain.EventParticipant;

public interface EventParticipantRepository {

    List<EventParticipant> listAll(Long eventId);

}
